package org.koitharu.kotatsu.parsers.site.madara.en

import org.koitharu.kotatsu.parsers.MangaLoaderContext
import org.koitharu.kotatsu.parsers.MangaSourceParser
import org.koitharu.kotatsu.parsers.model.MangaSource
import org.koitharu.kotatsu.parsers.site.madara.MadaraParser

@MangaSourceParser("MANGASTIC", "Mangastic", "en")
internal class Mangastic(context: MangaLoaderContext) :
	MadaraParser(context, MangaSource.MANGASTIC, "mangastic.cc", 20)
