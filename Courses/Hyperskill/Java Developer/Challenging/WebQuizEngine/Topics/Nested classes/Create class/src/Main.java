class StringOperations {

    // create static nested class EngString
    public static class EngString {
        boolean isEnglishLang;
        String string;

        public EngString(boolean isEnglishLang, String string) {
            this.isEnglishLang = isEnglishLang;
            this.string = string;
        }

        public boolean isEnglish() {
            return isEnglishLang;
        }

        public String getString() {
            return string;
        }
    }
}