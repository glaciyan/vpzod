package cc.glaciyan.vpzod;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectConfigurations {
  @SerializedName("projectId")
  @Expose()
  private String id;

  @SerializedName("schemaPath")
  @Expose()
  private String schemaPath;

  public ProjectConfigurations(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public String getSchemaPath() {
    return schemaPath;
  }

  public void setSchemaPath(String schemaPath) {
    this.schemaPath = schemaPath;
  }
}
