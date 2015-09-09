package login;

class LoginInformation {
    private String ppft;
    private String url;

    public LoginInformation(String ppft, String url) {
        this.ppft = ppft;
        this.url = url;
    }

    public String getPpft() {
        return ppft;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginInformation that = (LoginInformation) o;
        if (ppft != null ? !ppft.equals(that.ppft) : that.ppft != null) return false;
        return !(url != null ? !url.equals(that.url) : that.url != null);
    }

    @Override
    public int hashCode() {
        int result = ppft != null ? ppft.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LoginInformation{" +
                "ppft='" + ppft + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
