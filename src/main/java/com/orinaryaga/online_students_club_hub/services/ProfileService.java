package com.orinaryaga.online_students_club_hub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.orinaryaga.online_students_club_hub.models.Profile;
import com.orinaryaga.online_students_club_hub.repositories.ProfileRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    private final String FOLDER_PATH = "A:/online_students_club_hub/src/main/resources/image_db/";

    public String uploadImageToFileSystem(MultipartFile file) throws IOException {
        String filePath = FOLDER_PATH + file.getOriginalFilename();

        Profile profile = profileRepository.save(Profile.builder()
                .imageName(file.getOriginalFilename())
                .imageType(file.getContentType())
                .filePath(filePath).build());

        file.transferTo(new File(filePath));

        if (profile != null) {
            return "Profile uploaded succesfully" + filePath;
        }
        return null;
    }

    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<Profile> profile = profileRepository.findByimageName(fileName);
        String filePath = profile.get().getFilePath();
        return Files.readAllBytes(new File(filePath).toPath());
    }

    public void deleteImageFromFileSystem(String fileName) throws IOException {
        Optional<Profile> profile = profileRepository.findByimageName(fileName);
        if (profile.isPresent()) {
            File file = new File(profile.get().getFilePath());
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public void saveProfile(Profile profile) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveProfile'");
    }
}

