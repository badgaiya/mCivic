package manacle.mcivic.data.model

import java.io.Serializable

data class TownLayer1(
    var id: String,
    var name: String,
    var location_id: String,
    var company_id: String,
     var layerArrayList: ArrayList<Layer1>
):Serializable