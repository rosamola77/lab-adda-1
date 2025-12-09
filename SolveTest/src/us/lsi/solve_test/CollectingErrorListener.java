package us.lsi.solve_test;

import org.antlr.v4.runtime.*;

import java.util.ArrayList;
import java.util.List;

/**
 * CollectingErrorListener
 *
 * <p>Listener de errores de ANTLR que recolecta todos los errores
 * sintácticos en una lista para su posterior procesamiento.
 * A diferencia de imprimir errores directamente, los acumula
 * para permitir validación y reporte personalizado.</p>
 *
 * <p>Ejemplo de uso:
 * {@code
 * CollectingErrorListener errorListener = new CollectingErrorListener();
 * parser.addErrorListener(errorListener);
 * parser.parse();
 * if (errorListener.hasErrors()) {
 *     for (String error : errorListener.getErrors()) {
 *         System.out.println(error);
 *     }
 * }
 * }</p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see BaseErrorListener
 */
public class CollectingErrorListener extends BaseErrorListener {
    /**
     * Lista de errores recolectados durante el parsing.
     */
    private final List<String> errors = new ArrayList<>();

    /**
     * Maneja un error de sintaxis añadiéndolo a la lista de errores.
     *
     * @param recognizer el parser que detectó el error
     * @param offendingSymbol el símbolo que causó el error
     * @param line línea donde ocurrió el error
     * @param charPositionInLine posición de columna del error
     * @param msg mensaje descriptivo del error
     * @param e excepción de reconocimiento (puede ser null)
     */
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
                            Object offendingSymbol,
                            int line, int charPositionInLine,
                            String msg,
                            RecognitionException e) {
        String error = "Línea " + line + ":" + charPositionInLine + " - " + msg;
        errors.add(error);
    }

    /**
     * Verifica si se han recolectado errores.
     *
     * @return {@code true} si hay errores; {@code false} en caso contrario
     */
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    /**
     * Obtiene la lista de errores recolectados.
     *
     * @return lista de mensajes de error
     */
    public List<String> getErrors() {
        return errors;
    }
}

