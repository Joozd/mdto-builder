package nl.joozd.mdto.objects

/**
 * @property begripLabel De tekstweergave van het begrip dat is toegekend in de begrippenlijst.
 * @property begripCode De code die aan het begrip is toegekend in de begrippenlijst.
 * @property begripBegrippenlijst Een beschrijving van de begrippen die voor een bepaald toepassingsgebied gebruikt
 *  worden is opgesomd. Samen met hun betekenis en hun onderlinge relaties.
 */
data class BegripGegevens(
    val begripLabel: String,
    val begripCode: String? = null,
    val begripBegrippenlijst: VerwijzingGegevens
): MDTONode