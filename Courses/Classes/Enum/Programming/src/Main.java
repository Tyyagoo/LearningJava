// declare your enum here

enum Currency{
    USD(840),
    EUR(978),
    GBP(826),
    RUB(643),
    UAH(980),
    KZT(398),
    CAD(124),
    JPY(392),
    CNY(156);

    private final int code;

    Currency(int code) {
        this.code = code;
    }

    public int getCode(){
        return code;
    }
}