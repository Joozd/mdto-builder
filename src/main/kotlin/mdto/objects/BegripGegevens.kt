package nl.joozd.mdto.objects

/**
 * @property label De tekstweergave van het begrip dat is toegekend in de begrippenlijst.
 * @property code De code die aan het begrip is toegekend in de begrippenlijst.
 * @property begrippenlijst Een beschrijving van de begrippen die voor een bepaald toepassingsgebied gebruikt
 *  worden is opgesomd. Samen met hun betekenis en hun onderlinge relaties.
 */
data class BegripGegevens(
    val label: String,
    val code: String? = null,
    val begrippenlijst: VerwijzingGegevens
): MDTONode