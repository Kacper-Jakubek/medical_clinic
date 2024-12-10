package pl.wsb.lab.medicalclinic.doctor;

public enum MedicalSpecialty {
    FAMILY_MEDICINE("Podstawowa opieka zdrowotna dla osób i rodzin"),
    INTERNAL_MEDICINE("Ogólna opieka zdrowotna dla dorosłych, często w przypadku złożonych lub przewlekłych schorzeń"),
    PEDIATRICS("Opieka nad niemowlętami, dziećmi i młodzieżą"),
    CARDIOLOGY("Leczenie chorób serca i naczyń krwionośnych"),
    DERMATOLOGY("Diagnozowanie i leczenie chorób skóry"),
    EMERGENCY_MEDICINE("Nagła opieka medyczna w sytuacjach awaryjnych"),
    GASTROENTEROLOGY("Układ trawienny i przewód pokarmowy"),
    NEUROLOGY("Leczenie chorób mózgu, rdzenia kręgowego i układu nerwowego"),
    OBSTETRICS_GYNECOLOGY("Zdrowie reprodukcyjne kobiet, ciąża i poród"),
    ONCOLOGY("Diagnozowanie i leczenie nowotworów"),
    OPHTHALMOLOGY("Opieka nad oczami i wzrokiem"),
    ORTHOPEDICS("Układ mięśniowo-szkieletowy (kości, stawy, mięśnie)"),
    OTOLARYNGOLOGY("Opieka nad uszami, nosem i gardłem"),
    PATHOLOGY("Analiza tkanek i płynów ustrojowych w celu diagnozy"),
    PSYCHIATRY("Zdrowie psychiczne i zaburzenia behawioralne"),
    PULMONOLOGY("Płuca i układ oddechowy"),
    RADIOLOGY("Diagnostyka obrazowa i radiologiczna (np. rentgen, MRI)"),
    RHEUMATOLOGY("Choroby autoimmunologiczne i stawów"),
    SURGERY("Leczenie operacyjne chorób i urazów"),
    UROLOGY("Układ moczowy i męski układ rozrodczy");

    private final String description;

    MedicalSpecialty(String description) {
        this.description = description;
    }
}
