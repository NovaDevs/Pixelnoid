package pixelnoid;

public enum Estado {
    PANTALLA_INICIO("PANTALLA_INICIO", 0),
    INSTRUCCIONES("INSTRUCCIONES", 1),
    JUGANDO("JUGANDO", 2),
    PAUSA("PAUSA", 3),
    NIVEL_SUPERADO("NIVEL_SUPERADO", 4),
    GAME_OVER("GAME_OVER", 5),
    RANKING("RANKING", 6),
    JUEGO_SUPERADO("JUEGO_SUPERADO", 7);

    private Estado(final String s, final int n) {
    }
}
