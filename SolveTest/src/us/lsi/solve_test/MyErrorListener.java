package us.lsi.solve_test;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

/**
 * MyErrorListener
 *
 * <p>Listener personalizado para errores de sintaxis en ANTLR.
 * Extiende {@link BaseErrorListener} para proporcionar manejo
 * personalizado de errores sintácticos durante el parsing.</p>
 *
 * <p>Imprime los errores de sintaxis en la salida de error estándar
 * con formato legible indicando línea, columna y mensaje de error.</p>
 *
 * @author Miguel Toro
 * @version 1.0
 * @since 1.0
 * @see BaseErrorListener
 */
public class MyErrorListener extends BaseErrorListener {
    /**
     * Maneja errores de sintaxis durante el parsing.
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
        System.err.println("Error en línea " + line + ":" + charPositionInLine + " - " + msg);
    }
}

