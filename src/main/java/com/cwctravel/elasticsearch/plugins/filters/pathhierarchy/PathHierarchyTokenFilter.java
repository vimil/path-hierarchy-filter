package com.cwctravel.elasticsearch.plugins.filters.pathhierarchy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class PathHierarchyTokenFilter extends TokenFilter {
	private final boolean reverse;
	private final boolean outputSegments;
	private final char delimiter;
	private final char replacement;

	private final List<String> availableTokens;

	private final CharTermAttribute termAtt;

	protected PathHierarchyTokenFilter(TokenStream input, char delimiter, char replacement, boolean outputSegments, boolean reverse) {
		super(input);
		this.termAtt = addAttribute(CharTermAttribute.class);
		this.delimiter = delimiter;
		this.replacement = replacement;
		this.outputSegments = outputSegments;
		this.reverse = reverse;
		this.availableTokens = new ArrayList<String>();

	}

	@Override
	public boolean incrementToken() throws IOException {
		boolean tokensAvailable = false;
		if(this.input.incrementToken()) {
			String term = termAtt.toString();
			String[] parts = term.split(Pattern.quote(Character.toString(delimiter)));

			termAtt.setEmpty();
			if(outputSegments || parts.length == 1) {
				for(int i = 0; i < parts.length; i++) {
					availableTokens.add(parts[i]);
				}
			}

			StringBuilder termBuilder = new StringBuilder();
			if(reverse) {
				for(int i = parts.length - 2; i >= 0; i--) {
					termBuilder.delete(0, termBuilder.length());
					for(int j = i; j < parts.length; j++) {
						termBuilder.append(parts[j]);
						if(j < i) {
							termBuilder.append(replacement);
						}
					}
					availableTokens.add(termBuilder.toString());
				}
			}
			else {
				for(int i = 1; i < parts.length; i++) {
					termBuilder.delete(0, termBuilder.length());
					for(int j = 0; j <= i; j++) {
						termBuilder.append(parts[j]);
						if(j < i) {
							termBuilder.append(replacement);
						}
					}
					availableTokens.add(termBuilder.toString());
				}
			}
		}

		if(!availableTokens.isEmpty()) {
			String currentToken = availableTokens.remove(0);
			termAtt.append(currentToken);
			tokensAvailable = true;
		}

		return tokensAvailable;
	}

}
