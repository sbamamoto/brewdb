    package server

    enum MaterialType {

        MALT(1, "Malz"),
        HOP(2, "Hopfen"),
        WATER(3, "Wasser"),
        YEAST(4, "Hefe"),
        EQUIPMENT(4, "Ausrüstung"),
        OTHER(5, "Andere"),

        final int id
        final String value

        private MaterialType(int id, String value) {
            this.id = id
            this.value = value
        }

        // Might be needed for <g:select> tags, I'm not very sure
        int getKey() { id }

        static MaterialType byId(int id) {
            values().find { it.id == id }
        }
    }
