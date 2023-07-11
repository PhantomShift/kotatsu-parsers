package org.koitharu.kotatsu.parsers.site.madara.pt


import org.koitharu.kotatsu.parsers.MangaLoaderContext
import org.koitharu.kotatsu.parsers.MangaSourceParser
import org.koitharu.kotatsu.parsers.model.MangaSource
import org.koitharu.kotatsu.parsers.site.madara.MadaraParser

@MangaSourceParser("YCSCAN", "Ycscan", "pt")
internal class Ycscan(context: MangaLoaderContext) :
	MadaraParser(context, MangaSource.YCSCAN, "ycscan.com", 20) {

	override val isNsfwSource = true
	override val datePattern: String = "dd/MM/yyyy"
}
