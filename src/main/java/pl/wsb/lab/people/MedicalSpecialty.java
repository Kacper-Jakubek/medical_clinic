package pl.wsb.lab.people;

public enum MedicalSpecialty {
    FAMILY_MEDICINE("Primary care for individuals and families"),
    INTERNAL_MEDICINE("General adult care, often for complex or chronic conditions"),
    PEDIATRICS("Care for infants, children, and adolescents"),
    CARDIOLOGY("Treatment of heart and vascular diseases"),
    DERMATOLOGY("Diagnosis and treatment of skin disorders"),
    EMERGENCY_MEDICINE("Acute care for emergency medical situations"),
    GASTROENTEROLOGY("Digestive system and gastrointestinal tract"),
    NEUROLOGY("Treatment of the brain, spinal cord, and nervous system disorders"),
    OBSTETRICS_GYNECOLOGY("Women’s reproductive health, pregnancy, and childbirth"),
    ONCOLOGY("Diagnosis and treatment of cancer"),
    OPHTHALMOLOGY("Eye and vision care"),
    ORTHOPEDICS("Musculoskeletal system (bones, joints, muscles)"),
    OTOLARYNGOLOGY("Ear, nose, and throat care"),
    PATHOLOGY("Analysis of body tissues and fluids for diagnosis"),
    PSYCHIATRY("Mental health and behavioral disorders"),
    PULMONOLOGY("Lungs and respiratory system"),
    RADIOLOGY("Imaging and radiologic diagnosis (e.g., X-ray, MRI)"),
    RHEUMATOLOGY("Autoimmune and joint diseases"),
    SURGERY("Operative treatment of diseases and injuries"),
    UROLOGY("Urinary tract and male reproductive system");

    private final String description;

    MedicalSpecialty(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
