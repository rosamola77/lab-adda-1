package us.lsi.regularexpressions;

import us.lsi.regularexpressions.Tokenizer.TokenType;

/**
 * <p>Representa un token del analizador lexico.</p>
 * 
 * <p>Cada token tiene un texto, un tipo y una posicion
 * (inicio y fin) en la cadena original.</p>
 * 
 * @author Miguel Toro
 */
public class Token {
	
	/**
	 * Crea un nuevo Token.
	 * 
	 * @param text Texto del token
	 * @param type Tipo del token
	 * @param start Posicion de inicio
	 * @return Un nuevo Token
	 */
	public static Token of(String text, TokenType type, Integer start) {
		return new Token(text, type, start);
	}


	/** Texto del token */
	public String text;
	/** Tipo del token */
	public TokenType type;
	/** Posicion de inicio */
	public Integer start;
	/** Posicion de fin */
	public Integer end;
	
	
	/**
	 * Constructor privado.
	 * 
	 * @param text Texto del token
	 * @param type Tipo del token
	 * @param start Posicion de inicio
	 */
	private Token(String text, TokenType type, Integer start) {
		super();
		this.text = text;
		this.type = type;
		this.start = start;
		this.end = start+text.length();
	}


	/**
	 * Representacion en cadena del token.
	 * 
	 * @return Cadena con formato "tipo==texto"
	 */
	@Override
	public String toString() {
		return type+"==" + text;
	}

	


}
