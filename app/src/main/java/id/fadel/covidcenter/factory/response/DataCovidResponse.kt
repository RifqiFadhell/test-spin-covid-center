package id.fadel.covidcenter.factory.response

import com.google.gson.annotations.SerializedName

data class DataCovidResponse(

	@field:SerializedName("features")
	val features: List<FeaturesItem>? = null,

	@field:SerializedName("globalIdFieldName")
	val globalIdFieldName: String? = null,

	@field:SerializedName("objectIdFieldName")
	val objectIdFieldName: String? = null,

	@field:SerializedName("spatialReference")
	val spatialReference: SpatialReference? = null,

	@field:SerializedName("fields")
	val fields: List<FieldsItem>? = null,

	@field:SerializedName("uniqueIdField")
	val uniqueIdField: UniqueIdField? = null,

	@field:SerializedName("geometryType")
	val geometryType: String? = null
)

data class UniqueIdField(

	@field:SerializedName("isSystemMaintained")
	val isSystemMaintained: Boolean? = null,

	@field:SerializedName("name")
	val name: String? = null
)

data class FieldsItem(

	@field:SerializedName("sqlType")
	val sqlType: String? = null,

	@field:SerializedName("defaultValue")
	val defaultValue: Any? = null,

	@field:SerializedName("domain")
	val domain: Any? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("alias")
	val alias: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("length")
	val length: Int? = null
)

data class FeaturesItem(

	@field:SerializedName("attributes")
	val attributes: Attributes? = null,

	@field:SerializedName("geometry")
	val geometry: Geometry? = null
)

data class SpatialReference(

	@field:SerializedName("latestWkid")
	val latestWkid: Int? = null,

	@field:SerializedName("wkid")
	val wkid: Int? = null
)

data class Geometry(

	@field:SerializedName("x")
	val X: Double? = null,

	@field:SerializedName("y")
	val Y: Double? = null
)

data class Attributes(

	@field:SerializedName("FID")
	val fID: Int? = null,

	@field:SerializedName("Kode_Provi")
	val kodeProvi: Int? = null,

	@field:SerializedName("Kasus_Meni")
	val kasusMeni: Int = 0,

	@field:SerializedName("Kasus_Posi")
	val kasusPosi: Int = 0,

	@field:SerializedName("Provinsi")
	val provinsi: String? = null,

	@field:SerializedName("Kasus_Semb")
	val kasusSemb: Int = 0
)
