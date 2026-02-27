package nl.joozd.parsing

import nl.joozd.mdto.objects.IdentificatieGegevens

internal data class ObjectTypeBase(
    val identificaties: List<IdentificatieGegevens>,
    val naam: String,
)