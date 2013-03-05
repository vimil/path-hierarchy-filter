package com.cwctravel.elasticsearch.plugins.filters.pathhierarchy;

import org.apache.lucene.analysis.TokenStream;
import org.elasticsearch.ElasticSearchIllegalArgumentException;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.AbstractTokenFilterFactory;
import org.elasticsearch.index.analysis.AnalysisSettingsRequired;
import org.elasticsearch.index.settings.IndexSettings;

@AnalysisSettingsRequired
public class PathHierarchyTokenFilterFactory extends AbstractTokenFilterFactory {
	private final char delimiter;
	private final char replacement;
	private final boolean reverse;
	private final boolean outputSegments;

	@Inject
	public PathHierarchyTokenFilterFactory(Index index, @IndexSettings Settings indexSettings, @Assisted String name, @Assisted Settings settings) {
		super(index, indexSettings, name, settings);
		String delimiter = settings.get("delimiter");
		if(delimiter == null) {
			this.delimiter = '/';
		}
		else {
			if(delimiter.length() > 1) {
				throw new ElasticSearchIllegalArgumentException("delimiter can only be a one char value");
			}
			this.delimiter = delimiter.charAt(0);
		}

		String replacement = settings.get("replacement");
		if(replacement == null || replacement.length() == 0) {
			this.replacement = this.delimiter;
		}
		else {
			if(replacement.length() > 1) {
				throw new ElasticSearchIllegalArgumentException("replacement can only be a one char value");
			}
			this.replacement = replacement.charAt(0);
		}
		this.outputSegments = settings.getAsBoolean("outputSegments", Boolean.valueOf(true)).booleanValue();
		this.reverse = settings.getAsBoolean("reverse", Boolean.valueOf(false)).booleanValue();
	}

	@Override
	public TokenStream create(TokenStream tokenStream) {
		// TODO Auto-generated method stub
		return new PathHierarchyTokenFilter(tokenStream, delimiter, replacement, outputSegments, reverse);
	}

}
