package com.cardsagainsthumanity.Entities;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;

/** <p>This is a helper class for customized datatypes. It provides a
 * set of static methods which may be useful in custom methods for
 * parsing and printing values.</p>
 * <p>The JAXB provider is responsible to initialize the
 * <code>DatatypeConverter</code> class by invoking
 * {@link #setDatatypeConverter(DatatypeConverterInterface)} as soon
 * as possible.</p>
 *
 * @author JSR-31
 * @since JAXB1.0
 */
public final class DatatypeConverter {
  private static DatatypeConverter converter;

  /** <p>This method must be invoked by the JAXB provider to set the actual
   * instance, which is invoked by the static methods. Subsequent
   * invocations of the method are ignored: First come, first wins.</p>
   * @throws IllegalArgumentException The parameter was null.
   */
  public static void setDatatypeConverter(DatatypeConverter pConverter) {
    if (pConverter == null) {
      throw new IllegalArgumentException("The parameter must not be null.");
    }
    synchronized (DatatypeConverter.class) {
      if (converter == null) {
        converter = pConverter;
      }
    }
  }

  /** <p>Parses the lexical representation and converts it into a String.</p>
   *
   * @param pLexicalXSDString The input string being parsed.
   * @return The unmodified input string.
   * @see javax.xml.bind.ParseConversionEvent
   */
  public static String parseString(String pLexicalXSDString) {
    return DatatypeConverter.parseString(pLexicalXSDString);
  }

  /** <p>Parses the lexical representation of the given integer value
   * (arbitrary precision) and converts it into an instance of
   * {@link java.math.BigInteger}.</p>
   * @param pLexicalXSDInteger The input string being parsed.
   * @return The input string converted into an instance of {@link BigInteger}.
   * @see javax.xml.bind.ParseConversionEvent
   */
  public static BigInteger parseInteger(String pLexicalXSDInteger) {
    return DatatypeConverter.parseInteger(pLexicalXSDInteger);
  }

  /** <p>Parses the lexical representation of the given 32 bit integer value
   * and converts it into a primitive <code>int</code> value.</p>
   * @param pLexicalXSDInt The input string being parsed.
   * @return The input string converted into a primitive <code>int</code>.
   * @see javax.xml.bind.ParseConversionEvent
   */ 
  public static int parseInt(String pLexicalXSDInt) {
    return DatatypeConverter.parseInt(pLexicalXSDInt);
  }

  /** <p>Parses the lexical representation of the given 64 bit integer value
   * and converts it into a primitive <code>long</code> value.</p>
   * @param pLexicalXSDLong The input string being parsed.
   * @return The input string converted into a primitive <code>long</code>.
   * @see javax.xml.bind.ParseConversionEvent
   */ 
  public static long parseLong(String pLexicalXSDLong) {
    return DatatypeConverter.parseLong(pLexicalXSDLong);
  }

  /** <p>Parses the lexical representation of the given 16 bit integer value
   * and converts it into a primitive <code>short</code> value.</p>
   * @param pLexicalXSDShort The input string being parsed.
   * @return The input string converted into a primitive <code>short</code>.
   * @see javax.xml.bind.ParseConversionEvent
   */ 
  public static short parseShort(String pLexicalXSDShort) {
    return DatatypeConverter.parseShort(pLexicalXSDShort);
  }

  /** <p>Parses the lexical representation of the given decimal value
   * (arbitrary precision) and converts it into an instance of
   * {@link java.math.BigDecimal}.</p>
   * @param pLexicalXSDDecimal The input string being parsed.
   * @return The input string converted into an instance of {@link java.math.BigDecimal}.
   * @see javax.xml.bind.ParseConversionEvent
   */ 
  public static BigDecimal parseDecimal(String pLexicalXSDDecimal) {
    return DatatypeConverter.parseDecimal(pLexicalXSDDecimal);
  }

  /** <p>Parses the lexical representation of the given 32 bit floating
   * point value and converts it into a primitive <code>float</code> value.</p>
   * @param pLexicalXSDFloat The input string being parsed.
   * @return The input string converted into a primitive <code>float</code>.
   * @see javax.xml.bind.ParseConversionEvent
   */ 
  public static float parseFloat(String pLexicalXSDFloat) {
    return DatatypeConverter.parseFloat(pLexicalXSDFloat);
  }

  /** <p>Parses the lexical representation of the given 64 bit floating
   * point value and converts it into a primitive <code>double</code> value.</p>
   * @param pLexicalXSDDouble The input string being parsed.
   * @return The input string converted into a primitive <code>double</code>.
   * @see javax.xml.bind.ParseConversionEvent
   */ 
  public static double parseDouble(String pLexicalXSDDouble) {
    return DatatypeConverter.parseDouble(pLexicalXSDDouble);
  }

  /** <p>Parses the lexical representation of the given boolean value
   * and converts it into a primitive <code>boolean</code> value.</p>
   * @param pLexicalXSDBoolean The input string being parsed.
   * @return The input string converted into a primitive <code>boolean</code>.
   * @see javax.xml.bind.ParseConversionEvent
   */ 
  public static boolean parseBoolean(String pLexicalXSDBoolean) {
    return DatatypeConverter.parseBoolean(pLexicalXSDBoolean);
  }

  /** <p>Parses the lexical representation of the given 8 bit integer value
   * and converts it into a primitive <code>byte</code> value.</p>
   * @param pLexicalXSDByte The input string being parsed.
   * @return The input string converted into a primitive <code>byte</code>.
   * @see javax.xml.bind.ParseConversionEvent
   */ 
  public static byte parseByte(String pLexicalXSDByte) {
    return DatatypeConverter.parseByte(pLexicalXSDByte);
  }

  /** <p>Parses the lexical representation of the given qualified name
   * and converts it into an instance of {@link javax.xml.namespace.QName}.
   * The {@link javax.xml.namespace.QName} consists of a namespace URI
   * and a local name.</p>
   * @param pLexicalXSDQName The input string being parsed, an optional
   *   namespace prefix, followed by the local name, if any. If a prefix
   *   is present, they are separated by a colon.
   * @param pNamespaceContext The namespace context is used to query
   *   mappings between prefixes and namespace URI's.
   * @return The input string converted into an instance of
   *   {@link javax.xml.namespace.QName}.
   * @see javax.xml.bind.ParseConversionEvent
   */ 
  public static QName parseQName(String pLexicalXSDQName,
                                  NamespaceContext pNamespaceContext) {
    return DatatypeConverter.parseQName(pLexicalXSDQName, pNamespaceContext);                        
  }

  /** <p>Parses the lexical representation of the given dateTime value
   * and converts it into an instance of {@link java.util.Calendar}.
   * Valid lexical representations of a dateTime value include
   * <pre>
   *   YYYY-MM-DDThh:mm:ss
   *   YYYY-MM-DDThh:mm:ss.sss
   *   YYYY-MM-DDThh:mm:ssZ
   *   YYYY-MM-DDThh:mm:ss-01:00
   * </pre>
   * The former examples are all specified in UTC time. The last example
   * uses a negatice offset of one hour to UTC.</p>
   * @param pLexicalXSDDateTime The input string being parsed.
   * @return The input string converted into an instance of
   *   {@link java.util.Calendar}.
   * @see javax.xml.bind.ParseConversionEvent
   */
  public static Calendar parseDateTime(String pLexicalXSDDateTime) {
    return DatatypeConverter.parseDateTime(pLexicalXSDDateTime);
  }

  /** <p>Parses the lexical representation of the given byte array, which
   * is encoded in base 64.</p>
   * @param pLexicalXSDBase64Binary The input string being parsed, a
   *   base 64 encoded array of bytes.
   * @return The decoded byte array.
   * @see javax.xml.bind.ParseConversionEvent
   */
  public static byte[] parseBase64Binary(String pLexicalXSDBase64Binary) {
    return DatatypeConverter.parseBase64Binary(pLexicalXSDBase64Binary);
  }

  /** <p>Parses the lexical representation of the given byte array, which
   * is encoded in hex digits.</p>
   * @param pLexicalXSDHexBinary The input string being parsed, an
   *    array of bytes encoded in hex digits.
   * @return The decoded byte array.
   * @see javax.xml.bind.ParseConversionEvent
   */
  public static byte[] parseHexBinary(String pLexicalXSDHexBinary) {
    return DatatypeConverter.parseHexBinary(pLexicalXSDHexBinary);
  }

  /** <p>Parses the lexical representation of the given 32 bit
   * unsignet integer value and converts it into a primitive <code>long</code>
   * value.</p>
   * @param pLexicalXSDUnsignedInt The input string being parsed.
   * @return The input string converted into a primitive <code>long</code>.
   * @see javax.xml.bind.ParseConversionEvent
   */ 
  public static long parseUnsignedInt(String pLexicalXSDUnsignedInt) {
    return DatatypeConverter.parseUnsignedInt(pLexicalXSDUnsignedInt);
  }

  /** <p>Parses the lexical representation of the given 16 bit
   * unsignet integer value and converts it into a primitive <code>int</code>
   * value.</p>
   * @param pLexicalXSDUnsignedShort The input string being parsed.
   * @return The input string conve
   * rted into a primitive <code>int</code>.
   * @see javax.xml.bind.ParseConversionEvent
   */ 
  public static int parseUnsignedShort(String pLexicalXSDUnsignedShort) {
    return DatatypeConverter.parseUnsignedShort(pLexicalXSDUnsignedShort);
  }

  /** <p>Parses the lexical representation of the given time value
   * and converts it into an instance of {@link java.util.Calendar}.
   * Valid lexical representations of a time value include
   * <pre>
   *   hh:mm:ss
   *   hh:mm:ss.sss
   *   hh:mm:ssZ
   *   hh:mm:ss-01:00
   * </pre>
   * The former examples are all specified in UTC time. The last example
   * uses a negatice offset of one hour to UTC.</p>
   * @param pLexicalXSDTime The input string being parsed.
   * @return The input string converted into an instance of
   *   {@link java.util.Calendar}.
   * @see javax.xml.bind.ParseConversionEvent
   */
  public static Calendar parseTime(String pLexicalXSDTime) {
    return DatatypeConverter.parseTime(pLexicalXSDTime);
  }

  /** <p>Parses the lexical representation of the given date value
   * and converts it into an instance of {@link java.util.Calendar}.
   * Valid lexical representations of a date value include
   * <pre>
   *   YYYY-MM-DD
   *   YYYY-MM-DDZ
   *   YYYY-MM-DD-01:00
   * </pre>
   * The former examples are all specified in UTC time. The last example
   * uses a negatice offset of one hour to UTC.</p>
   * @param pLexicalXSDDate The input string being parsed.
   * @return The input string converted into an instance of
   *   {@link java.util.Calendar}.
   * @see javax.xml.bind.ParseConversionEvent
   */
  public static Calendar parseDate(String pLexicalXSDDate) {
    return DatatypeConverter.parseDate(pLexicalXSDDate);
  }

  /** <p>Returns the lexical representation of the input string, which is
   * the unmodified input string.</p>
   * @param pLexicalXSDAnySimpleType An input string in lexical representation.
   * @return The unmodified input string.
   * @see javax.xml.bind.ParseConversionEvent
   */
  public static String parseAnySimpleType(String pLexicalXSDAnySimpleType) {
    return DatatypeConverter.parseAnySimpleType(pLexicalXSDAnySimpleType);
  }

  /** <p>Returns a lexical representation of the given input string, which
   * is the unmodified input string.</p>
   * @param pValue The input string.
   * @return The unmodified input string.
   * @see javax.xml.bind.PrintConversionEvent
   */
  public static String printString(String pValue) {
    return DatatypeConverter.printString(pValue);
  }

  /** <p>Returns a lexical representation of the given instance of
   * {@link BigInteger}, which is an integer in arbitrary precision.</p>
   * @param pValue The integer value being converted.
   * @return A lexical representation of the input value.
   * @see javax.xml.bind.PrintConversionEvent
   */
  public static String printInteger(BigInteger pValue) {
    return DatatypeConverter.printInteger(pValue);
  }

  /** <p>Returns a lexical representation of the given primitive
   * 32 bit integer.</p>
   * @param pValue The <code>int</code> value being converted.
   * @return A lexical representation of the input value.
   * @see javax.xml.bind.PrintConversionEvent
   */
  public static String printInt(int pValue) {
    return DatatypeConverter.printInt(pValue);
  }

  /** <p>Returns a lexical representation of the given primitive
   * 64 bit integer.</p>
   * @param pValue The <code>long</code> value being converted.
   * @return A lexical representation of the input value.
   * @see javax.xml.bind.PrintConversionEvent
   */
  public static String printLong(long pValue) {
    return DatatypeConverter.printLong(pValue);
  }

  /** <p>Returns a lexical representation of the given primitive
   * 16 bit integer.</p>
   * @param pValue The <code>short</code> value being converted.
   * @return A lexical representation of the input value.
   * @see javax.xml.bind.PrintConversionEvent
   */
  public static String printShort(short pValue) {
    return DatatypeConverter.printShort(pValue);
  }

  /** <p>Returns a lexical representation of the given instance of
   * {@link BigDecimal}, which is a decimal number in arbitrary
   * precision.</p>
   * @param pValue The decimal value being converted.
   * @return A lexical representation of the input value.
   * @see javax.xml.bind.PrintConversionEvent
   */
  public static String printDecimal(BigDecimal pValue) {
    return DatatypeConverter.printDecimal(pValue);
  }

  /** <p>Returns a lexical representation of the given primitive
   * 32 bit floating point number.</p>
   * @param pValue The <code>float</code> value being converted.
   * @return A lexical representation of the input value.
   * @see javax.xml.bind.PrintConversionEvent
   */
  public static String printFloat(float pValue) {
    return DatatypeConverter.printFloat(pValue);
  }

  /** <p>Returns a lexical representation of the given primitive
   * 64 bit floating point number.</p>
   * @param pValue The <code>double</code> value being converted.
   * @return A lexical representation of the input value.
   * @see javax.xml.bind.PrintConversionEvent
   */
  public static String printDouble(double pValue) {
    return DatatypeConverter.printDouble(pValue);
  }

  /** <p>Returns a lexical representation of the given primitive
   * boolean value.</p>
   * @param pValue The <code>boolean</code> value being converted.
   * @return A lexical representation of the input value.
   * @see javax.xml.bind.PrintConversionEvent
   */
  public static String printBoolean(boolean pValue) {
    return DatatypeConverter.printBoolean(pValue);
  }

  /** <p>Returns a lexical representation of the given primitive
   * 8 bit integer.</p>
   * @param pValue The <code>byte</code> value being converted.
   * @return A lexical representation of the input value.
   * @see javax.xml.bind.PrintConversionEvent
   */
  public static String printByte(byte pValue) {
    return DatatypeConverter.printByte(pValue);
  }

  /** <p>Returns a lexical representation of the given qualified
   * name, which is a combination of namespace URI and local name.
   * The lexical representation is an optional prefix, which is
   * currently mapped to namespace URI of the qualified name,
   * followed by a colon and the local name. If the namespace URI
   * is the current default namespace URI, then the prefix and
   * the colon may be omitted.</p>
   * @param pValue The qualified name being converted.
   * @param pNamespaceContext A mapping of prefixes to namespace
   *   URI's which may be used to determine a valid prefix.
   * @return A lexical representation of the qualified name.
   * @see javax.xml.bind.PrintConversionEvent
   */
  public static String printQName(QName pValue,
                                   NamespaceContext pNamespaceContext) {
    return DatatypeConverter.printQName(pValue, pNamespaceContext);                            
  }

  /** <p>Returns a lexical representation of the given dateTime
   * value. Valid lexical representations include:
   * <pre>
   *   YYYY-MM-DDThh:mm:ss
   *   YYYY-MM-DDThh:mm:ss.sss
   *   YYYY-MM-DDThh:mm:ssZ
   *   YYYY-MM-DDThh:mm:ss-01:00
   * </pre>
   * The former examples are all specified in UTC time. The last example
   * uses a negatice offset of one hour to UTC.</p>
   * @param pValue The dateTime value being converted
   * @return A lexical representation of the input value.
   * @see javax.xml.bind.PrintConversionEvent
   */
  public static String printDateTime(Calendar pValue) {
    return DatatypeConverter.printDateTime(pValue);
  }

  /** <p>Returns a lexical representation of the given byte array.
   * The lexical representation is obtained by application of the
   * base 64 encoding.</p>
   * @param pValue The byte array being converted.
   * @return The converted byte array.
   * @see javax.xml.bind.PrintConversionEvent
   */
  public static String printBase64Binary(byte[] pValue) {
    return DatatypeConverter.printBase64Binary(pValue);
  }

  /** <p>Returns a lexical representation of the given byte array.
   * The lexical representation is obtained by encoding any byte
   * as two hex digits.</p>
   * @param pValue The byte array being converted.
   * @return The converted byte array.
   * @see javax.xml.bind.PrintConversionEvent
   */
  public static String printHexBinary(byte[] pValue) {
    return DatatypeConverter.printHexBinary(pValue);
  }

  /** <p>Returns a lexical representation of the given primitive,
   * unsigned 32 bit integer.</p>
   * @param pValue The <code>long</code> value being converted.
   * @return A lexical representation of the input value.
   * @see javax.xml.bind.PrintConversionEvent
   */
  public static String printUnsignedInt(long pValue) {
    return DatatypeConverter.printUnsignedInt(pValue);
  }

  /** <p>Returns a lexical representation of the given primitive,
   * unsigned 16 bit integer.</p>
   * @param pValue The <code>short</code> value being converted.
   * @return A lexical representation of the input value.
   * @see javax.xml.bind.PrintConversionEvent
   */
  public static String printUnsignedShort(int pValue) {
    return DatatypeConverter.printUnsignedShort(pValue);
  }

  /** <p>Returns a lexical representation of the given time
   * value. Valid lexical representations include:
   * <pre>
   *   hh:mm:ss
   *   hh:mm:ss.sss
   *   hh:mm:ssZ
   *   hh:mm:ss-01:00
   * </pre>
   * The former examples are all specified in UTC time. The last example
   * uses a negatice offset of one hour to UTC.</p>
   * @param pValue The time value being converted
   * @return A lexical representation of the input value.
   * @see javax.xml.bind.PrintConversionEvent
   */
  public static String printTime(Calendar pValue) {
    return DatatypeConverter.printTime(pValue);
  }

  /** <p>Returns a lexical representation of the given date
   * value. Valid lexical representations include:
   * <pre>
   *   YYYY-MM-DD
   *   YYYY-MM-DDZ
   *   YYYY-MM-DD-01:00
   * </pre>
   * The former examples are all specified in UTC time. The last example
   * uses a negatice offset of one hour to UTC.</p>
   * @param pValue The date value being converted
   * @return A lexical representation of the input value.
   * @see javax.xml.bind.PrintConversionEvent
   */
  public static String printDate(Calendar pValue) {
    return DatatypeConverter.printDate(pValue);
  }

  /** <p>Returns a lexical representation of the given input
   * string, which is the unmodified input string.</p>
   * @param pValue The input string.
   * @return The unmodified input string.
   * @see javax.xml.bind.PrintConversionEvent
   */
  public static String printAnySimpleType(String pValue) {
    return DatatypeConverter.printAnySimpleType(pValue);
  }
}
