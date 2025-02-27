package org.koitharu.kotatsu.parsers.site.madara.tr

import org.koitharu.kotatsu.parsers.MangaLoaderContext
import org.koitharu.kotatsu.parsers.MangaSourceParser
import org.koitharu.kotatsu.parsers.model.MangaSource
import org.koitharu.kotatsu.parsers.site.madara.MadaraParser

@MangaSourceParser("WEBTOONEVRENI", "WebtoonEvreni", "tr")
internal class Webtoonevreni(context: MangaLoaderContext) :
	MadaraParser(context, MangaSource.WEBTOONEVRENI, "webtoonevreni.net", 10)
