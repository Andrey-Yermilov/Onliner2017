package webdriver;

public class Translit {

    public static String cyr2lat(char ch){
        switch (ch){
            case 'а': return "a";
            case 'б': return "b";
            case 'в': return "v";
            case 'г': return "g";
            case 'д': return "d";
            case 'е': return "e";
            case 'ё': return "jo";
            case 'ж': return "zh";
            case 'з': return "z";
            case 'и': return "i";
            case 'й': return "y";
            case 'к': return "k";
            case 'л': return "l";
            case 'м': return "m";
            case 'н': return "n";
            case 'о': return "o";
            case 'п': return "p";
            case 'р': return "r";
            case 'с': return "s";
            case 'т': return "t";
            case 'у': return "u";
            case 'ф': return "f";
            case 'х': return "kh";
            case 'ц': return "c";
            case 'ч': return "ch";
            case 'ш': return "sh";
            case 'щ': return "jsh";
            case 'ъ': return "";
            case 'ы': return "i";
            case 'ь': return "'";
            case 'э': return "e";
            case 'ю': return "ju";
            case 'я': return "ja";
            default: return String.valueOf(ch);
        }
    }

    /**
     * Перевести русский текст в транслит
     * @param ruString строка
     * @return транслитерированная строка
     */
    public static String toTranslit (String ruString){
        ruString = ruString.toLowerCase();
        StringBuilder stringBuilder = new StringBuilder(ruString.length()*2);
        for(char ch: ruString.toCharArray()){
            stringBuilder.append(cyr2lat(ch));
        }
        String s = stringBuilder.toString();
        s = s.substring(0,1).toUpperCase()+s.substring(1,s.length());
        return s;
    }
}
