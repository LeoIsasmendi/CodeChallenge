package android.example.com.codechallenge003.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "cast",
        "crew",
        "id"
})
public class TvShowCredits {

    @JsonProperty("cast")
    private List<Character> cast = null;
    @JsonProperty("crew")
    private List<Object> crew = null;
    @JsonProperty("id")
    private Integer id;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("cast")
    public List<Character> getCast() {
        return cast;
    }

    @JsonProperty("cast")
    public void setCast(List<Character> cast) {
        this.cast = cast;
    }

    @JsonProperty("crew")
    public List<Object> getCrew() {
        return crew;
    }

    @JsonProperty("crew")
    public void setCrew(List<Object> crew) {
        this.crew = crew;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}