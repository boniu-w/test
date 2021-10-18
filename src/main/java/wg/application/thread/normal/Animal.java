package wg.application.thread.normal;

public abstract class Animal {

    /* 科 */
    private String family;

    /* 种 */
    private String species;

    /* 名 */
    private String name;

    /* 性别 */
    private String gender;

    protected abstract void features();

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
