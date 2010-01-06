/* The following code was generated by JFlex 1.4.3 on 12/17/09 11:00 AM */

/*
 * Copyright 2007 - 2009 Lars Heuer (heuer[at]semagia.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ontopia.topicmaps.utils.jtm;

/**
 * INTERNAL: JSON lexer for JTM 1.0 documents.
 */
@SuppressWarnings("unused")

/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 12/17/09 11:00 AM from the specification file
 * <tt>/home/tn/workspace/ontopia/src/java/net/ontopia/topicmaps/utils/jtm/jtm.flex</tt>
 */
class JSONLexer {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0, 0
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\3\1\2\1\0\1\3\1\1\22\0\1\3\1\0\1\4"+
    "\11\0\1\37\2\0\1\6\12\0\1\40\40\0\1\11\1\5\1\12"+
    "\1\0\1\24\1\0\1\30\1\35\1\27\1\33\1\14\1\34\2\0"+
    "\1\17\1\36\1\0\1\31\1\23\1\21\1\20\1\26\1\0\1\15"+
    "\1\16\1\22\1\32\1\13\2\0\1\25\1\0\1\7\1\0\1\10"+
    "\uff82\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\2\2\1\1\1\3\1\4\1\5\1\6"+
    "\1\1\1\7\1\10\1\0\1\11\54\0\1\12\33\0"+
    "\1\13\6\0\1\14\1\0\1\15\1\16\4\0\1\17"+
    "\14\0\1\20\1\21\1\22\2\0\1\23\1\0\1\24"+
    "\6\0\1\25\6\0\1\26\3\0\1\27\12\0\1\30"+
    "\4\0\1\31\12\0\1\32\1\33\2\0\1\34";

  private static int [] zzUnpackAction() {
    int [] result = new int[171];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\41\0\102\0\41\0\143\0\41\0\41\0\41"+
    "\0\41\0\204\0\41\0\41\0\245\0\41\0\306\0\347"+
    "\0\u0108\0\u0129\0\u014a\0\u016b\0\u018c\0\u01ad\0\u01ce\0\u01ef"+
    "\0\u0210\0\u0231\0\u0252\0\u0273\0\u0294\0\u02b5\0\u02d6\0\u02f7"+
    "\0\u0318\0\u0339\0\u035a\0\u037b\0\u039c\0\u03bd\0\u03de\0\u03ff"+
    "\0\u0420\0\u0441\0\u0462\0\u0483\0\u04a4\0\u04c5\0\u04e6\0\u0507"+
    "\0\u0528\0\u0549\0\u056a\0\u058b\0\u05ac\0\u05cd\0\u05ee\0\u060f"+
    "\0\u0630\0\u0651\0\41\0\u0672\0\u0693\0\u06b4\0\u06d5\0\u06f6"+
    "\0\u0717\0\u0738\0\u0759\0\u077a\0\u079b\0\u07bc\0\u07dd\0\u07fe"+
    "\0\u081f\0\u0840\0\u0861\0\u0882\0\u08a3\0\u08c4\0\u08e5\0\u0906"+
    "\0\u0927\0\u0948\0\u0969\0\u098a\0\u09ab\0\u09cc\0\41\0\u09ed"+
    "\0\u0a0e\0\u0a2f\0\u0a50\0\u0a71\0\u0a92\0\41\0\u0ab3\0\41"+
    "\0\41\0\u0ad4\0\u0af5\0\u0b16\0\u0b37\0\41\0\u0b58\0\u0b79"+
    "\0\u0b9a\0\u0bbb\0\u0bdc\0\u0bfd\0\u0c1e\0\u0c3f\0\u0c60\0\u0c81"+
    "\0\u0ca2\0\u0cc3\0\41\0\41\0\41\0\u0ce4\0\u0d05\0\41"+
    "\0\u0d26\0\41\0\u0d47\0\u0d68\0\u0d89\0\u0daa\0\u0dcb\0\u0dec"+
    "\0\41\0\u0e0d\0\u0e2e\0\u0e4f\0\u0e70\0\u0e91\0\u0eb2\0\41"+
    "\0\u0ed3\0\u0ef4\0\u0f15\0\41\0\u0f36\0\u0f57\0\u0f78\0\u0f99"+
    "\0\u0fba\0\u0fdb\0\u0ffc\0\u101d\0\u103e\0\u105f\0\41\0\u1080"+
    "\0\u10a1\0\u10c2\0\u10e3\0\41\0\u1104\0\u1125\0\u1146\0\u1167"+
    "\0\u1188\0\u11a9\0\u11ca\0\u11eb\0\u120c\0\u122d\0\41\0\41"+
    "\0\u124e\0\u126f\0\41";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[171];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\2\1\3\2\4\1\5\2\2\1\6\1\7\1\10"+
    "\1\11\6\2\1\12\15\2\1\13\1\14\43\0\1\4"+
    "\36\0\4\15\1\16\1\17\5\15\1\20\1\15\1\21"+
    "\1\22\1\23\1\24\1\25\1\26\3\15\1\27\1\15"+
    "\1\30\2\15\1\31\5\15\32\0\1\32\6\0\4\15"+
    "\1\16\1\17\33\15\4\0\3\15\6\0\1\15\3\0"+
    "\2\15\7\0\1\15\6\0\4\15\1\16\1\17\6\15"+
    "\1\33\13\15\1\34\14\15\1\16\1\17\6\15\1\35"+
    "\3\15\1\36\24\15\1\16\1\17\21\15\1\37\2\15"+
    "\1\40\12\15\1\16\1\17\14\15\1\41\22\15\1\16"+
    "\1\17\21\15\1\42\15\15\1\16\1\17\22\15\1\43"+
    "\14\15\1\16\1\17\12\15\1\44\4\15\1\45\17\15"+
    "\1\16\1\17\22\15\1\46\1\47\13\15\1\16\1\17"+
    "\10\15\1\50\26\15\1\16\1\17\22\15\1\51\10\15"+
    "\31\0\1\52\7\0\4\15\1\16\1\17\7\15\1\53"+
    "\27\15\1\16\1\17\7\15\1\54\13\15\1\55\13\15"+
    "\1\16\1\17\11\15\1\56\25\15\1\16\1\17\23\15"+
    "\1\57\13\15\1\16\1\17\12\15\1\60\24\15\1\16"+
    "\1\17\27\15\1\61\7\15\1\16\1\17\6\15\1\62"+
    "\30\15\1\16\1\17\21\15\1\63\15\15\1\16\1\17"+
    "\15\15\1\64\21\15\1\16\1\17\20\15\1\65\16\15"+
    "\1\16\1\17\20\15\1\66\16\15\1\16\1\17\7\15"+
    "\1\67\27\15\1\16\1\17\22\15\1\70\14\15\1\16"+
    "\1\17\10\15\1\71\26\15\1\16\1\17\14\15\1\72"+
    "\16\15\31\0\1\73\7\0\4\15\1\16\1\17\10\15"+
    "\1\74\26\15\1\16\1\17\11\15\1\75\25\15\1\16"+
    "\1\17\24\15\1\76\12\15\1\16\1\17\26\15\1\77"+
    "\10\15\1\16\1\17\6\15\1\100\30\15\1\16\1\17"+
    "\20\15\1\101\16\15\1\16\1\17\30\15\1\102\6\15"+
    "\1\16\1\17\15\15\1\103\21\15\1\16\1\17\24\15"+
    "\1\104\12\15\1\16\1\17\6\15\1\105\30\15\1\16"+
    "\1\17\11\15\1\106\25\15\1\16\1\17\6\15\1\107"+
    "\30\15\1\16\1\17\6\15\1\110\30\15\1\16\1\17"+
    "\17\15\1\111\17\15\1\16\1\17\12\15\1\112\24\15"+
    "\1\16\1\17\22\15\1\113\14\15\1\16\1\17\11\15"+
    "\1\114\25\15\1\16\1\17\22\15\1\115\14\15\1\16"+
    "\1\17\6\15\1\116\30\15\1\16\1\17\11\15\1\117"+
    "\25\15\1\16\1\17\10\15\1\120\26\15\1\16\1\17"+
    "\6\15\1\121\30\15\1\16\1\17\6\15\1\122\30\15"+
    "\1\16\1\17\16\15\1\123\20\15\1\16\1\17\7\15"+
    "\1\124\27\15\1\16\1\17\10\15\1\125\26\15\1\16"+
    "\1\17\21\15\1\126\15\15\1\127\1\17\37\15\1\16"+
    "\1\17\13\15\1\130\23\15\1\16\1\17\6\15\1\131"+
    "\30\15\1\16\1\17\21\15\1\132\15\15\1\16\1\17"+
    "\14\15\1\133\22\15\1\16\1\17\12\15\1\134\24\15"+
    "\1\16\1\17\13\15\1\135\23\15\1\136\1\17\37\15"+
    "\1\16\1\17\6\15\1\137\30\15\1\140\1\17\37\15"+
    "\1\141\1\17\37\15\1\16\1\17\21\15\1\142\15\15"+
    "\1\16\1\17\11\15\1\143\2\15\1\144\22\15\1\16"+
    "\1\17\7\15\1\145\27\15\1\146\1\17\37\15\1\16"+
    "\1\17\10\15\1\147\26\15\1\16\1\17\14\15\1\150"+
    "\22\15\1\16\1\17\7\15\1\151\27\15\1\16\1\17"+
    "\11\15\1\152\25\15\1\16\1\17\17\15\1\153\17\15"+
    "\1\16\1\17\13\15\1\154\23\15\1\16\1\17\14\15"+
    "\1\155\22\15\1\16\1\17\7\15\1\156\27\15\1\16"+
    "\1\17\14\15\1\157\22\15\1\16\1\17\25\15\1\160"+
    "\11\15\1\16\1\17\17\15\1\161\17\15\1\16\1\17"+
    "\6\15\1\162\30\15\1\163\1\17\37\15\1\164\1\17"+
    "\37\15\1\165\1\17\37\15\1\16\1\17\22\15\1\166"+
    "\14\15\1\16\1\17\20\15\1\167\16\15\1\170\1\17"+
    "\37\15\1\16\1\17\10\15\1\171\26\15\1\172\1\17"+
    "\37\15\1\16\1\17\16\15\1\173\20\15\1\16\1\17"+
    "\6\15\1\174\30\15\1\16\1\17\20\15\1\175\16\15"+
    "\1\16\1\17\13\15\1\176\23\15\1\16\1\17\14\15"+
    "\1\177\22\15\1\16\1\17\6\15\1\200\30\15\1\201"+
    "\1\17\37\15\1\16\1\17\11\15\1\202\11\15\1\203"+
    "\13\15\1\16\1\17\13\15\1\204\23\15\1\16\1\17"+
    "\6\15\1\205\30\15\1\16\1\17\21\15\1\206\15\15"+
    "\1\16\1\17\11\15\1\207\25\15\1\210\1\17\37\15"+
    "\1\16\1\17\25\15\1\211\11\15\1\16\1\17\12\15"+
    "\1\212\24\15\1\16\1\17\14\15\1\213\22\15\1\214"+
    "\1\17\37\15\1\16\1\17\6\15\1\215\30\15\1\16"+
    "\1\17\12\15\1\216\24\15\1\16\1\17\6\15\1\217"+
    "\30\15\1\16\1\17\21\15\1\220\15\15\1\16\1\17"+
    "\11\15\1\221\25\15\1\16\1\17\10\15\1\222\26\15"+
    "\1\16\1\17\13\15\1\223\23\15\1\16\1\17\13\15"+
    "\1\224\23\15\1\16\1\17\22\15\1\225\14\15\1\16"+
    "\1\17\26\15\1\226\10\15\1\227\1\17\37\15\1\16"+
    "\1\17\10\15\1\230\26\15\1\16\1\17\14\15\1\231"+
    "\22\15\1\16\1\17\14\15\1\232\22\15\1\16\1\17"+
    "\11\15\1\233\25\15\1\234\1\17\37\15\1\16\1\17"+
    "\11\15\1\235\25\15\1\16\1\17\12\15\1\236\24\15"+
    "\1\16\1\17\6\15\1\237\30\15\1\16\1\17\26\15"+
    "\1\240\10\15\1\16\1\17\7\15\1\241\27\15\1\16"+
    "\1\17\7\15\1\242\27\15\1\16\1\17\11\15\1\243"+
    "\25\15\1\16\1\17\10\15\1\244\26\15\1\16\1\17"+
    "\10\15\1\245\26\15\1\16\1\17\6\15\1\246\30\15"+
    "\1\247\1\17\37\15\1\250\1\17\37\15\1\16\1\17"+
    "\7\15\1\251\27\15\1\16\1\17\10\15\1\252\26\15"+
    "\1\253\1\17\33\15";

  private static int [] zzUnpackTrans() {
    int [] result = new int[4752];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\1\11\1\1\1\11\1\1\4\11\1\1\2\11"+
    "\1\0\1\11\54\0\1\11\33\0\1\11\6\0\1\11"+
    "\1\0\2\11\4\0\1\11\14\0\3\11\2\0\1\11"+
    "\1\0\1\11\6\0\1\11\6\0\1\11\3\0\1\11"+
    "\12\0\1\11\4\0\1\11\12\0\2\11\2\0\1\11";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[171];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */

    public static final int EOF = YYEOF;

    private int _leftOffset;
    private int _rightOffset;

    public String value() {
        return new String(zzBuffer, zzStartRead+_leftOffset, yylength()-_leftOffset-_rightOffset);
    }

    private int _token(final int type) {
        return _token(type, 0, 0);
    }

    private int _token(final int type, final int leftOffset, final int rightOffset) {
        _leftOffset = leftOffset;
        _rightOffset = rightOffset;
        return type;
    }


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  JSONLexer(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  JSONLexer(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 102) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead > 0) {
      zzEndRead+= numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream    
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }     
    }

	// numRead < 0
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public int token() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 19: 
          { return _token(JSONToken.KW_VERSION);
          }
        case 29: break;
        case 16: 
          { return _token(JSONToken.KW_TOPICS);
          }
        case 30: break;
        case 2: 
          { /* noop */
          }
        case 31: break;
        case 8: 
          { return _token(JSONToken.COLON);
          }
        case 32: break;
        case 7: 
          { return _token(JSONToken.COMMA);
          }
        case 33: break;
        case 23: 
          { return _token(JSONToken.KW_ITEM_TYPE);
          }
        case 34: break;
        case 28: 
          { return _token(JSONToken.KW_SIDS);
          }
        case 35: break;
        case 21: 
          { return _token(JSONToken.KW_VARIANTS);
          }
        case 36: break;
        case 25: 
          { return _token(JSONToken.KW_ASSOCIATIONS);
          }
        case 37: break;
        case 11: 
          { return _token(JSONToken.KW_TYPE);
          }
        case 38: break;
        case 5: 
          { return _token(JSONToken.START_ARRAY);
          }
        case 39: break;
        case 13: 
          { return _token(JSONToken.KW_ROLES);
          }
        case 40: break;
        case 17: 
          { return _token(JSONToken.KW_PARENT);
          }
        case 41: break;
        case 18: 
          { return _token(JSONToken.KW_PLAYER);
          }
        case 42: break;
        case 3: 
          { return _token(JSONToken.START_OBJECT);
          }
        case 43: break;
        case 9: 
          { return _token(JSONToken.VALUE_STRING, 1, 1);
          }
        case 44: break;
        case 6: 
          { return _token(JSONToken.END_ARRAY);
          }
        case 45: break;
        case 1: 
          { throw new Error("Illegal character <" + yytext() + ">");
          }
        case 46: break;
        case 4: 
          { return _token(JSONToken.END_OBJECT);
          }
        case 47: break;
        case 22: 
          { return _token(JSONToken.KW_DATATYPE);
          }
        case 48: break;
        case 20: 
          { return _token(JSONToken.KW_REIFIER);
          }
        case 49: break;
        case 26: 
          { return _token(JSONToken.KW_SLOS);
          }
        case 50: break;
        case 15: 
          { return _token(JSONToken.KW_NAMES);
          }
        case 51: break;
        case 12: 
          { return _token(JSONToken.KW_VALUE);
          }
        case 52: break;
        case 10: 
          { return _token(JSONToken.VALUE_NULL);
          }
        case 53: break;
        case 14: 
          { return _token(JSONToken.KW_SCOPE);
          }
        case 54: break;
        case 27: 
          { return _token(JSONToken.KW_IIDS);
          }
        case 55: break;
        case 24: 
          { return _token(JSONToken.KW_OCCURRENCES);
          }
        case 56: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            return YYEOF;
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}