package models

import scala.collection.mutable
import scalikejdbc._
import scalikejdbc._
import com.ponkotuy.data.master
import util.scalikejdbc.BulkInsert._

case class MasterShipSpecs(
  id: Int,
  hp: Int,
  soukoMin: Int,
  soukoMax: Int,
  tousai: Int,
  karyokuMin: Int,
  karyokuMax: Int,
  raisouMin: Int,
  raisouMax: Int,
  taikuMin: Int,
  taikuMax: Int,
  taisenMin: Int,
  taisenMax: Int,
  kaihiMin: Int,
  kaihiMax: Int,
  sakutekiMin: Int,
  sakutekiMax: Int,
  luckyMin: Int,
  luckyMax: Int,
  sokuh: Int,
  soku: Int,
  length: Int) {

  def save()(implicit session: DBSession = MasterShipSpecs.autoSession): MasterShipSpecs = MasterShipSpecs.save(this)(session)

  def destroy()(implicit session: DBSession = MasterShipSpecs.autoSession): Unit = MasterShipSpecs.destroy(this)(session)

}


object MasterShipSpecs extends SQLSyntaxSupport[MasterShipSpecs] {

  val cache = new mutable.WeakHashMap[Int, Option[MasterShipSpecs]] with mutable.SynchronizedMap[Int, Option[MasterShipSpecs]]

  override val tableName = "master_ship_specs"

  override val columns = Seq("id", "hp", "souko_min", "souko_max", "tousai", "karyoku_min", "karyoku_max", "raisou_min", "raisou_max", "taiku_min", "taiku_max", "taisen_min", "taisen_max", "kaihi_min", "kaihi_max", "sakuteki_min", "sakuteki_max", "lucky_min", "lucky_max", "sokuh", "soku", "length")

  def apply(mss: ResultName[MasterShipSpecs])(rs: WrappedResultSet): MasterShipSpecs = new MasterShipSpecs(
    id = rs.int(mss.id),
    hp = rs.int(mss.hp),
    soukoMin = rs.int(mss.soukoMin),
    soukoMax = rs.int(mss.soukoMax),
    tousai = rs.int(mss.tousai),
    karyokuMin = rs.int(mss.karyokuMin),
    karyokuMax = rs.int(mss.karyokuMax),
    raisouMin = rs.int(mss.raisouMin),
    raisouMax = rs.int(mss.raisouMax),
    taikuMin = rs.int(mss.taikuMin),
    taikuMax = rs.int(mss.taikuMax),
    taisenMin = rs.int(mss.taisenMin),
    taisenMax = rs.int(mss.taisenMax),
    kaihiMin = rs.int(mss.kaihiMin),
    kaihiMax = rs.int(mss.kaihiMax),
    sakutekiMin = rs.int(mss.sakutekiMin),
    sakutekiMax = rs.int(mss.sakutekiMax),
    luckyMin = rs.int(mss.luckyMin),
    luckyMax = rs.int(mss.luckyMax),
    sokuh = rs.int(mss.sokuh),
    soku = rs.int(mss.soku),
    length = rs.int(mss.length)
  )

  val mss = MasterShipSpecs.syntax("mss")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[MasterShipSpecs] = {
    def query = withSQL {
      select.from(MasterShipSpecs as mss).where.eq(mss.id, id)
    }.map(MasterShipSpecs(mss.resultName)).single().apply()
    cache.getOrElseUpdate(id, query)
  }

  def findAll()(implicit session: DBSession = autoSession): List[MasterShipSpecs] = {
    withSQL(select.from(MasterShipSpecs as mss)).map(MasterShipSpecs(mss.resultName)).list().apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls"count(1)").from(MasterShipSpecs as mss)).map(rs => rs.long(1)).single().apply().get
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[MasterShipSpecs] = {
    withSQL {
      select.from(MasterShipSpecs as mss).where.append(sqls"${where}")
    }.map(MasterShipSpecs(mss.resultName)).list().apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls"count(1)").from(MasterShipSpecs as mss).where.append(sqls"${where}")
    }.map(_.long(1)).single().apply().get
  }

  def create(
    id: Int,
    hp: Int,
    soukoMin: Int,
    soukoMax: Int,
    tousai: Int,
    karyokuMin: Int,
    karyokuMax: Int,
    raisouMin: Int,
    raisouMax: Int,
    taikuMin: Int,
    taikuMax: Int,
    taisenMin: Int,
    taisenMax: Int,
    kaihiMin: Int,
    kaihiMax: Int,
    sakutekiMin: Int,
    sakutekiMax: Int,
    luckyMin: Int,
    luckyMax: Int,
    sokuh: Int,
    soku: Int,
    length: Int)(implicit session: DBSession = autoSession): MasterShipSpecs = {
    withSQL {
      insert.into(MasterShipSpecs).columns(
        column.id,
        column.hp,
        column.soukoMin,
        column.soukoMax,
        column.tousai,
        column.karyokuMin,
        column.karyokuMax,
        column.raisouMin,
        column.raisouMax,
        column.taikuMin,
        column.taikuMax,
        column.taisenMin,
        column.taisenMax,
        column.kaihiMin,
        column.kaihiMax,
        column.sakutekiMin,
        column.sakutekiMax,
        column.luckyMin,
        column.luckyMax,
        column.sokuh,
        column.soku,
        column.length
      ).values(
          id,
          hp,
          soukoMin,
          soukoMax,
          tousai,
          karyokuMin,
          karyokuMax,
          raisouMin,
          raisouMax,
          taikuMin,
          taikuMax,
          taisenMin,
          taisenMax,
          kaihiMin,
          kaihiMax,
          sakutekiMin,
          sakutekiMax,
          luckyMin,
          luckyMax,
          sokuh,
          soku,
          length
        )
    }.update().apply()

    MasterShipSpecs(
      id = id,
      hp = hp,
      soukoMin = soukoMin,
      soukoMax = soukoMax,
      tousai = tousai,
      karyokuMin = karyokuMin,
      karyokuMax = karyokuMax,
      raisouMin = raisouMin,
      raisouMax = raisouMax,
      taikuMin = taikuMin,
      taikuMax = taikuMax,
      taisenMin = taisenMin,
      taisenMax = taisenMax,
      kaihiMin = kaihiMin,
      kaihiMax = kaihiMax,
      sakutekiMin = sakutekiMin,
      sakutekiMax = sakutekiMax,
      luckyMin = luckyMin,
      luckyMax = luckyMax,
      sokuh = sokuh,
      soku = soku,
      length = length)
  }

  def bulkInsert(xs: Seq[master.MasterShipSpecs])(implicit session: DBSession = autoSession): Unit = {
    require(xs.nonEmpty)
    applyUpdate {
      insert.into(MasterShipSpecs)
        .columns(
          column.id, column.hp, column.soukoMin, column.soukoMax, column.tousai, column.karyokuMin, column.karyokuMax,
          column.raisouMin, column.raisouMax, column.taikuMin, column.taikuMax, column.taisenMin, column.taisenMax,
          column.kaihiMin, column.kaihiMax, column.sakutekiMin, column.sakutekiMax, column.luckyMin, column.luckyMax,
          column.sokuh, column.soku, column.length
        )
        .multiValues(
          xs.map(_.id), xs.map(_.hp), xs.map(_.soukoMin), xs.map(_.soukoMax), xs.map(_.tousai),
          xs.map(_.karyokuMin), xs.map(_.karyokuMax), xs.map(_.raisouMin), xs.map(_.raisouMax),
          xs.map(_.taikuMin), xs.map(_.taikuMax), xs.map(_.taisenMin), xs.map(_.taisenMax),
          xs.map(_.kaihiMin), xs.map(_.kaihiMax), xs.map(_.sakutekiMin), xs.map(_.sakutekiMax),
          xs.map(_.luckyMin), xs.map(_.luckyMax), xs.map(_.sokuh), xs.map(_.soku), xs.map(_.length)
        )
    }
  }

  def save(entity: MasterShipSpecs)(implicit session: DBSession = autoSession): MasterShipSpecs = {
    withSQL {
      update(MasterShipSpecs).set(
        column.id -> entity.id,
        column.hp -> entity.hp,
        column.soukoMin -> entity.soukoMin,
        column.soukoMax -> entity.soukoMax,
        column.tousai -> entity.tousai,
        column.karyokuMin -> entity.karyokuMin,
        column.karyokuMax -> entity.karyokuMax,
        column.raisouMin -> entity.raisouMin,
        column.raisouMax -> entity.raisouMax,
        column.taikuMin -> entity.taikuMin,
        column.taikuMax -> entity.taikuMax,
        column.taisenMin -> entity.taisenMin,
        column.taisenMax -> entity.taisenMax,
        column.kaihiMin -> entity.kaihiMin,
        column.kaihiMax -> entity.kaihiMax,
        column.sakutekiMin -> entity.sakutekiMin,
        column.sakutekiMax -> entity.sakutekiMax,
        column.luckyMin -> entity.luckyMin,
        column.luckyMax -> entity.luckyMax,
        column.sokuh -> entity.sokuh,
        column.soku -> entity.soku,
        column.length -> entity.length
      ).where.eq(column.id, entity.id)
    }.update().apply()
    entity
  }

  def destroy(entity: MasterShipSpecs)(implicit session: DBSession = autoSession): Unit = {
    withSQL {
      delete.from(MasterShipSpecs).where.eq(column.id, entity.id)
    }.update().apply()
  }

  def deleteAll()(implicit session: DBSession = autoSession): Unit = {
    withSQL {
      delete.from(MasterShipSpecs)
    }.update().apply()
  }

}
