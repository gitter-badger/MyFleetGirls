package models

import scalikejdbc._
import util.scalikejdbc.BulkInsert._
import com.ponkotuy.data
import dat.ShipWithName

case class SlotItem(
  memberId: Long,
  id: Int,
  slotitemId: Int,
  name: String) {

  def save()(implicit session: DBSession = SlotItem.autoSession): SlotItem = SlotItem.save(this)(session)

  def destroy()(implicit session: DBSession = SlotItem.autoSession): Unit = SlotItem.destroy(this)(session)

}


object SlotItem extends SQLSyntaxSupport[SlotItem] {

  override val tableName = "slot_item"

  override val columns = Seq("member_id", "id", "slotitem_id", "name")

  def apply(si: ResultName[SlotItem])(rs: WrappedResultSet): SlotItem = new SlotItem(
    memberId = rs.long(si.memberId),
    id = rs.int(si.id),
    slotitemId = rs.int(si.slotitemId),
    name = rs.string(si.name)
  )

  lazy val si = SlotItem.syntax("si")
  lazy val ssi = ShipSlotItem.syntax("ssi")
  lazy val s = Ship.syntax("s")
  lazy val ms = MasterShipBase.syntax("ms")
  lazy val mst = MasterStype.syntax("mst")

  override val autoSession = AutoSession

  def find(id: Int, memberId: Long)(implicit session: DBSession = autoSession): Option[SlotItem] = {
    withSQL {
      select.from(SlotItem as si).where.eq(si.id, id).and.eq(si.memberId, memberId)
    }.map(SlotItem(si.resultName)).single().apply()
  }

  def findIn(xs: Seq[Int], memberId: Long)(implicit session: DBSession = autoSession): Seq[SlotItem] = {
    xs match {
      case Seq() => Nil
      case _ =>
        val result = withSQL {
          select.from(SlotItem as si)
            .where.in(si.id, xs).and.eq(si.memberId, memberId)
        }.map(SlotItem(si.resultName)).list().apply()
        xs.flatMap(id => result.find(_.id == id)) // sort
    }
  }

  def findAll()(implicit session: DBSession = autoSession): List[SlotItem] = {
    withSQL(select.from(SlotItem as si)).map(SlotItem(si.resultName)).list().apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls"count(1)").from(SlotItem as si)).map(rs => rs.long(1)).single().apply().get
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[SlotItem] = {
    withSQL {
      select.from(SlotItem as si).where.append(sqls"${where}")
    }.map(SlotItem(si.resultName)).list().apply()
  }

  def findAllArmedShipBy(where: SQLSyntax)(implicit sessin: DBSession = autoSession): List[ShipWithName] = {
    withSQL {
      select.from(SlotItem as si)
        .innerJoin(ShipSlotItem as ssi).on(sqls"${si.id} = ${ssi.slotitemId} and ${si.memberId} = ${ssi.memberId}")
        .leftJoin(Ship as s).on(sqls"${ssi.shipId} = ${s.id} and ${ssi.memberId} = ${s.memberId}")
        .leftJoin(MasterShipBase as ms).on(s.shipId, ms.id)
        .leftJoin(MasterStype as mst).on(ms.stype, mst.id)
        .where.append(where)
    }.map { rs =>
      val memberId = rs.long(ssi.resultName.memberId)
      val shipId = rs.int(s.resultName.shipId)
      val slot = Ship.findSlot(memberId, shipId)
      ShipWithName(Ship(s, slot)(rs), MasterShipBase(ms)(rs), MasterStype(mst)(rs))
    }.toList().apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls"count(1)").from(SlotItem as si).where.append(sqls"${where}")
    }.map(_.long(1)).single().apply().get
  }

  def countItemBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[(MiniItem, Long)] = {
    withSQL {
      select(si.slotitemId, si.name, sqls"count(1) as count").from(SlotItem as si)
        .where.append(sqls"${where}")
        .groupBy(si.slotitemId)
    }.map(rs => MiniItem(rs.int(1), rs.string(2)) -> rs.long(3)).toList().apply()
  }

  def create(
    memberId: Long,
    id: Int,
    slotitemId: Int,
    name: String)(implicit session: DBSession = autoSession): SlotItem = {
    withSQL {
      insert.into(SlotItem).columns(
        column.memberId,
        column.id,
        column.slotitemId,
        column.name
      ).values(
          memberId,
          id,
          slotitemId,
          name
        )
    }.update().apply()

    SlotItem(
      memberId = memberId,
      id = id,
      slotitemId = slotitemId,
      name = name)
  }

  def bulkInsert(xs: Seq[data.SlotItem], memberId: Long)(implicit session: DBSession = autoSession): Unit = {
    val masterNames = MasterSlotItem.findAll().map(msi => msi.id -> msi.name).toMap
    val names = xs.map(x => masterNames(x.slotitemId))
    applyUpdate {
      insert.into(SlotItem)
        .columns(column.memberId, column.id, column.slotitemId, column.name)
        .multiValues(Seq.fill(xs.size)(memberId), xs.map(_.id), xs.map(_.slotitemId), names)
    }
  }

  def save(entity: SlotItem)(implicit session: DBSession = autoSession): SlotItem = {
    withSQL {
      update(SlotItem).set(
        column.memberId -> entity.memberId,
        column.id -> entity.id,
        column.slotitemId -> entity.slotitemId,
        column.name -> entity.name
      ).where.eq(column.id, entity.id).and.eq(column.memberId, entity.memberId)
    }.update().apply()
    entity
  }

  def destroy(entity: SlotItem)(implicit session: DBSession = autoSession): Unit = {
    withSQL {
      delete.from(SlotItem).where.eq(column.id, entity.id).and.eq(column.memberId, entity.memberId)
    }.update().apply()
  }

  def deleteAllByUser(memberId: Long)(implicit session: DBSession = autoSession): Unit = applyUpdate {
    delete.from(SlotItem).where.eq(column.memberId, memberId)
  }

}

case class MiniItem(id: Int, name: String)
