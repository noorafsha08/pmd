/*
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd.lang.ast.impl.antlr4;

import java.io.IOException;
import java.io.Reader;

import org.antlr.v4.runtime.Lexer;

import net.sourceforge.pmd.lang.Parser;
import net.sourceforge.pmd.lang.ParserOptions;
import net.sourceforge.pmd.lang.ast.ParseException;
import net.sourceforge.pmd.lang.ast.RootNode;

/**
 * Generic Antlr parser adapter for all Antlr parsers. This wraps a parser
 * generated by antlr, soo {@link PmdAntlrParserBase}.
 *
 * @param <P> Type of the wrapped parser
 * @param <N> Supertype of all nodes for the language, eg SwiftNode
 * @param <R> Type of the root node
 */
public abstract class AntlrBaseParser<
    P extends org.antlr.v4.runtime.Parser,
    I extends AntlrNode,
    N extends AntlrBaseInnerNode<N, I>,
    R extends AntlrBaseInnerNode<N, I> & RootNode> implements Parser {

    protected final ParserOptions parserOptions;

    public AntlrBaseParser(final ParserOptions parserOptions) {
        this.parserOptions = parserOptions;
    }

    @Override
    public ParserOptions getParserOptions() {
        return parserOptions;
    }

    @Override
    public R parse(final String fileName, final Reader source) throws ParseException {
        try {
            Lexer lexer = getLexer(source);
            P parser = getParser(lexer);
            return parse(parser);
        } catch (final IOException e) {
            throw new ParseException(e);
        }
    }

    protected abstract R parse(P parser);

    protected abstract Lexer getLexer(Reader source) throws IOException;

    protected abstract P getParser(Lexer lexer);
}
