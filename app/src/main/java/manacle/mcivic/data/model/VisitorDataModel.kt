package manacle.mcivic.data.model

data class VisitorDataModel(
    val id: String,
    val company_id: String,
    val visitor_mobile: String,
    val visitor_name: String,
    val visitor_schedule_time: String,
    val visitor_stay_period: String,
    val visitor_vehicle_type: String,
    val visitor_vehicle_number: String,
    val status: String,
    val created_at: String,
    val created_by: String,
    val updated_at: String,
    val updated_by: String
)