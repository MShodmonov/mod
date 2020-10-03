//package uz.mod.component;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
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
//    public void run(String... args) throws Exception {
//        Role user = new Role(RoleEnumeration.ROLE_USER);
//        Role admin = new Role(RoleEnumeration.ROLE_ADMIN);
//        List<Role> roles = new LinkedList<Role>();
//        roles.add(user);
//        roles.add(admin);
//        roleRepository.save(user);
//        roleRepository.save(admin);
//
//        Category category = categoryRepo.save(new Category("Algebra","Algebra in russian"));
//        Conception conception = conceptionRepo.save(new Conception("1. conception",category));
//        Conception conception1 = conceptionRepo.save(new Conception("2. conception",category));
//        Conception conception2 = conceptionRepo.save(new Conception("3. conception",category));
//        Conception conception3 = conceptionRepo.save(new Conception("4. conception",category));
//        List<Conception>list = new LinkedList<>();
//        list.add(conception);
//        list.add(conception1);
//        list.add(conception2);
//        list.add(conception3);
////        List<District>listDistrict = new LinkedList<>();
////        District district = districtRepo.save(new District("Chilonzor"));
////        District district2 = districtRepo.save(new District("Yunusobod"));
////        listDistrict.add(district);
////        listDistrict.add(district2);
//
//
//
//      //  Region region = regionRepo.save(new Region("Toshkent shahar",listDistrict));
//
//
//        Subject subject = subjectRepo.save(new Subject("Algebra 10 th grade", list,category));
//
////        PostConception postConception = postConceptionRepo.save(new PostConception("Musobek Shodmonov",district,region,"some school",true,"this is  TEST",false,conception));
////        PostConception postConception2 = postConceptionRepo.save(new PostConception("Musobek Shodmonov",district,region,"some school",true,"this is  TEST",false,conception));
//
//
//
//
//        User root = new User("root",passwordEncoder.encode("root123"),roles);
//        userRepository.save(root);
//    }
//
//}
