package de.dom.cishome.myapplication.compose.shared

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import de.dom.cishome.myapplication.compose.shared.GsonUtils.Companion.fromJson
import de.dom.cishome.myapplication.tm.application.domain.player.model.Player
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class GsonUtils {


    companion object {


        fun Object.toJson() : String{
            return GsonUtils.mapper().toJson(this);
        }

        fun <T> String.fromJson( clazz: Class<T> ): T {
            return GsonUtils.mapper().fromJson( this, clazz )
        }

        fun Player.toJson(): String {
            return GsonUtils.mapper().toJson( this ) ?: "{}";
        }

        fun mapper(): Gson {
            class LocalDateAdapter : JsonSerializer<LocalDate> {
                override fun serialize(
                    date: LocalDate,
                    typeOfSrc: Type,
                    context: JsonSerializationContext
                ): JsonElement {
                    return JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE)) // "yyyy-mm-dd"
                }
            }

            class LocalDateDeserializer : JsonDeserializer<LocalDate>{
                override fun deserialize(
                    json: JsonElement?,
                    typeOfT: Type?,
                    context: JsonDeserializationContext?
                ): LocalDate? {
                    if( json == null ) return null;
                    var p: LocalDate = LocalDate.parse( json.asString , DateTimeFormatter.ISO_DATE )
                    return p;
                }
            }

            val GSON: Gson = GsonBuilder()
                .registerTypeAdapter( LocalDate::class.java , LocalDateDeserializer() )
                .registerTypeAdapter( LocalDate::class.java , LocalDateAdapter() )
                .create();
            return GSON;
        }

    }

}