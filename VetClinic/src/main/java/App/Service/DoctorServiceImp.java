package App.Service;

import App.Entity.Doctor;
import App.Entity.WorkingHours;
import App.Repository.ConsultationRepository;
import App.Repository.DoctorRepository;
import App.Repository.UserRepository;
import App.Repository.WorkingHoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
public class DoctorServiceImp implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final WorkingHoursRepository workingHoursRepository;
    private final ConsultationRepository consultationRepository;

    @Autowired
    public DoctorServiceImp(DoctorRepository doctorRepository, WorkingHoursRepository workingHoursRepository,
                            ConsultationRepository consultationRepository) {
        this.doctorRepository = doctorRepository;
        this.workingHoursRepository = workingHoursRepository;
        this.consultationRepository = consultationRepository;
    }

    @Override
    public List<Doctor> getDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public void addDoctor(Doctor doctor) {
        Doctor doctor1 = new Doctor();
        doctor1.setAge(doctor.getAge());
        doctor1.setFirstName(doctor.getFirstName());
        doctor1.setSpeciality(doctor.getSpeciality());
        doctor1.setLastName(doctor.getLastName());
        doctor1.setYearsOfExperience(doctor.getYearsOfExperience());
        doctor1.getUser().setEmail(doctor.getUser().getEmail());
        doctor1.getUser().setPassword(doctor.getUser().getPassword());

        doctorRepository.save(doctor1);
        System.out.println("ADDED!");
    }

    @Transactional
    @Override
    public void deleteDoctor(int id) {
        List<WorkingHours> workingHoursList = workingHoursRepository.getAllByDoctor_Id(id);
        for(WorkingHours workingHours : workingHoursList){
            consultationRepository.deleteAllByWorkingHours(workingHours);
        }
        workingHoursRepository.deleteAllByDoctor_Id(id);
        doctorRepository.deleteById(id);
        System.out.println("Delete!");
    }

    @Override
    public void updateDoctor(Doctor doctor, int id) {
        doctor.setId(id);
        doctorRepository.save(doctor);
    }

    @Override
    public Doctor getDoctor(int id) {
        return doctorRepository.getDoctorById(id);
    }
}
