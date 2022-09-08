package main.TestCases;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "executionStart",
        "executionEnd",
        "duration",
        "result"
})
@Generated("jsonschema2pojo")
public class Report {

    @JsonProperty("name")
    private String name;
    @JsonProperty("executionStart")
    private String executionStart;
    @JsonProperty("executionEnd")
    private String executionEnd;
    @JsonProperty("duration")
    private String duration;
    @JsonProperty("result")
    private String result;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("executionStart")
    public String getExecutionStart() {
        return executionStart;
    }

    @JsonProperty("executionStart")
    public void setExecutionStart(String executionStart) {
        this.executionStart = executionStart;
    }

    @JsonProperty("executionEnd")
    public String getExecutionEnd() {
        return executionEnd;
    }

    @JsonProperty("executionEnd")
    public void setExecutionEnd(String executionEnd) {
        this.executionEnd = executionEnd;
    }

    @JsonProperty("duration")
    public String getDuration() {
        return duration;
    }

    @JsonProperty("duration")
    public void setDuration(String duration) {
        this.duration = duration;
    }

    @JsonProperty("result")
    public String getResult() {
        return result;
    }

    @JsonProperty("result")
    public void setResult(String result) {
        this.result = result;
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
