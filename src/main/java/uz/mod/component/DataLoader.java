//package uz.mod.component;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import org.springframework.transaction.annotation.Transactional;
//import uz.mod.entity.*;
//import uz.mod.entity.enums.RoleEnumeration;
//import uz.mod.repository.*;
//
//import java.util.LinkedList;
//import java.util.List;
//
//@Component
//public class DataLoader implements CommandLineRunner {
//    @Autowired
//    private UserRepo userRepository;
//
//    @Autowired
//    private RoleRepo roleRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private CategoryRepo categoryRepo;
//
//    @Autowired
//    private ConceptionRepo conceptionRepo;
//
//    @Autowired
//    private SubjectRepo subjectRepo;
//
//    @Autowired
//    private BookRepo bookRepo;
//
//    @Autowired
//    private PostConceptionRepo postConceptionRepo;
//
//    @Autowired
//    private ImageRepo imageRepo;
//
//    @Autowired
//    private PostBookRepo postBookRepo;
//
//    @Autowired
//    private DistrictRepo districtRepo;
//
//    @Autowired
//    private RegionRepo regionRepo;
//
//
//
//
//
//
//
//
//
//    @Override
//    @Transactional
//    public void run(String... args) throws Exception {
//        Role user = new Role(RoleEnumeration.ROLE_USER);
//        Role admin = new Role(RoleEnumeration.ROLE_ADMIN);
//        List<Role> roles = new LinkedList<Role>();
//        roles.add(user);
//        roles.add(admin);
//        roleRepository.save(user);
//        roleRepository.save(admin);
//
//        Category category1 = categoryRepo.save(new Category("Language","Language in russian"));
//        Category category2 = categoryRepo.save(new Category("Exact subject","Exact subject in russian"));
//        Category category3 = categoryRepo.save(new Category("Some Cat","Some Cat in russian"));
//        Subject subject1 = subjectRepo.save(new Subject("English 10 th grade","English 10 th grade",category1));
//        Subject subject2 = subjectRepo.save(new Subject("Algebra 10 th grade","Algebra 10 th grade",category2));
//        Subject subject3 = subjectRepo.save(new Subject("History 10 th grade","History 10 th grade",category3));
//        Subject subject4 = subjectRepo.save(new Subject("Russian 10 th grade","Russian 10 th grade",category1));
//        Subject subject5 = subjectRepo.save(new Subject("Physics 10 th grade","Physics 10 th grade",category2));
//        Subject subject6 = subjectRepo.save(new Subject("Philosophy 10 th grade","Philosophy 10 th grade",category3));
//        Subject subject7 = subjectRepo.save(new Subject("Germany 10 th grade","Germany 10 th grade",category1));
//        Subject subject8 = subjectRepo.save(new Subject("Geometry 10 th grade","Geometry 10 th grade",category2));
//        Subject subject9 = subjectRepo.save(new Subject("Some Subject 10 th grade","Some Subject 10 th grade",category3));
//        Subject subject10 = subjectRepo.save(new Subject("Spain 10 th grade","Spain 10 th grade",category1));
//
//
//        Conception conception = conceptionRepo.save(new Conception("1. conception","1. conception"));
//        Conception conception1 = conceptionRepo.save(new Conception("2. conception","2. conception"));
//        Conception conception2 = conceptionRepo.save(new Conception("3. conception","3. conception"));
//        Conception conception3 = conceptionRepo.save(new Conception("4. conception","4. conception"));
//        Conception conception4 = conceptionRepo.save(new Conception("1. conception","1. conception"));
//        Conception conception5 = conceptionRepo.save(new Conception("2. conception","2. conception"));
//        Conception conception6 = conceptionRepo.save(new Conception("3. conception","3. conception"));
//        Conception conception7 = conceptionRepo.save(new Conception("4. conception","4. conception"));
//        Conception conception8 = conceptionRepo.save(new Conception("1. conception","1. conception"));
//        Conception conception9 = conceptionRepo.save(new Conception("2. conception","2. conception"));
//        Conception conception10 = conceptionRepo.save(new Conception("3. conception","3. conception"));
//        Conception conception11 = conceptionRepo.save(new Conception("4. conception","4. conception"));
//        List<Conception>list = new LinkedList<>();
//        list.add(conception);
//        list.add(conception1);
//        list.add(conception2);
//        list.add(conception3);
//
//
//
//
//
//
//
//
//
//
//
//        User root = new User("root",passwordEncoder.encode("root123"),roles);
//        userRepository.save(root);
//    }
//
//}
