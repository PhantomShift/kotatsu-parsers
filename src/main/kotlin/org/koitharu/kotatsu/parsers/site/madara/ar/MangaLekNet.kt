package org.koitharu.kotatsu.parsers.site.madara.ar

import org.koitharu.kotatsu.parsers.MangaLoaderContext
import org.koitharu.kotatsu.parsers.MangaSourceParser
import org.koitharu.kotatsu.parsers.model.MangaSource
import org.koitharu.kotatsu.parsers.site.madara.MadaraParser

@MangaSourceParser("MANGALEK_NET", "Manga Lek .Net", "ar")
internal class MangaLekNet(context: MangaLoaderContext) :
	MadaraParser(context, MangaSource.MANGALEK_NET, "manga-lek.net", pageSize = 10)
