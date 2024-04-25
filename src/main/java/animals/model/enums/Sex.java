package animals.model.enums;

public enum Sex {
        MALE,
        FEMALE;
        
    public static Sex fromString(String value) {
        for (Sex sexName : Sex.values()) {
            if (sexName.name().equalsIgnoreCase(value)) {
                return sexName;
            }
        }
        throw new IllegalArgumentException("Can't find sex " + value);
    }

    @Override
    public String toString() {

        return name();
    }
}
