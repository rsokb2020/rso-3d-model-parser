package si.fri.rso.kb6750.model3dparser.lib;

public class Model3dBinaryData {
    public String getBinaryArrayString() {
        return binaryArrayString;
    }

    public void setBinaryArrayString(String binaryArrayString) {
        this.binaryArrayString = binaryArrayString;
    }

    private String binaryArrayString;
    private String assetBundleBinaryArray;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    public String getAssetBundleBinaryArray() {
        return assetBundleBinaryArray;
    }

    public void setAssetBundleBinaryArray(String assetBundleBinaryArray) {
        this.assetBundleBinaryArray = assetBundleBinaryArray;
    }
}
