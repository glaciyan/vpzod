package cc.glaciyan.vpzod;

public class VisibilityUtils {
  private VisibilityUtils() {
    throw new AssertionError("No VisibilityUtils for you!");
  }

  public static Visibility getVisibility(String visibility) {
    switch (visibility) {
      case "private":
        return Visibility.Private;
      case "protected":
        return Visibility.Protected;
      case "package":
        return Visibility.Package;
      case "public":
        return Visibility.Public;
      default:
        return Visibility.Unspecified;
    }
  }
}
