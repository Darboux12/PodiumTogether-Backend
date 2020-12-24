package com.podium.dal.file;

import com.podium.dal.file.exception.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Repository
public class FileRepository {

    public String saveImageAndGetPath(MultipartFile image){

        if(this.isAcceptedImagesType(image.getContentType())){

        String uniqueFileName = this.getUniqueImageFileName(image.getOriginalFilename());

        String path = this.getImagesDirectoryPath() + "\\" + uniqueFileName;

        File storeImage = new File(path);

            try {
                image.transferTo(storeImage);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return this.findImagePathByName(uniqueFileName);

        }

        throw new PodiumNotSupportedImageType();

    }

    public String saveDocumentAndGetPath(MultipartFile document){

        if(this.isAcceptedDocumentType(document.getContentType())){

            String uniqueFileName = this.getUniqueDocumentFileName(document.getOriginalFilename());

            String path = this.getDocumentsDirectoryPath() + "\\" + uniqueFileName;

            File storeDocument = new File(path);

            try {
                document.transferTo(storeDocument);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return this.findDocumentPathByName(uniqueFileName);

        }

        throw new PodiumNotSupportedDocumentType();

    }

    public boolean isAcceptedImagesType(String imageType){
        return this.getAcceptedImagesTypes().contains(imageType);
    }

    public boolean isAcceptedDocumentType(String documentType){
        return this.getAcceptedDocumentsTypes().contains(documentType);
    }

    public void deleteFile(String filePath){

        File file = new File(filePath);

        if(!file.delete())
            throw new PodiumFileDeleteFailException();

    }

    public boolean existFileByPath(String path){
        File f = new File(path);
        return f.exists() && !f.isDirectory();
    }

    private String findDocumentPathByName(String documentName){
        return this.findFilePath(documentName,this.getDocumentsDirectoryPath());
    }

    private String findImagePathByName(String imageName){
        return this.findFilePath(imageName,this.getImagesDirectoryPath());
    }

    private String findFilePath(String fileName, String directoryPath){

        File f = new File(directoryPath);

        File[] matchingFiles = f.listFiles((dir, name) -> name.equals(fileName));

        if(matchingFiles == null)
            throw new PodiumFileNotExistException();

        if(matchingFiles.length > 1)
            throw new PodiumMoreThanOneFileException();

        return matchingFiles[0].getPath();

    }

    private String getUniqueImageFileName(String fileName) {

        int num = 0;

        final String ext = getFileExtension(fileName);

        final String name = getFileName(fileName);

        File file = new File(this.getImagesDirectoryPath(), fileName);

        while (file.exists()) {
            num++;
            file = new File(this.getImagesDirectoryPath(), name + "-" + num + ext);
        }

        return file.getName();
    }

    private String getUniqueDocumentFileName(String fileName) {

        int num = 0;

        final String ext = getFileExtension(fileName);

        final String name = getFileName(fileName);

        File file = new File(this.getDocumentsDirectoryPath(), fileName);

        while (file.exists()) {
            num++;
            file = new File(this.getDocumentsDirectoryPath(), name + "-" + num + ext);
        }

        return file.getName();
    }

    private String getFileExtension(final String path) {
        if (path != null && path.lastIndexOf('.') != -1) {
            return path.substring(path.lastIndexOf('.'));
        }
        return null;
    }

    private String getFileName(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf('.'));
    }

    private List<String> getAcceptedImagesTypes(){

        return List.of(
                "image/jpeg",
                "image/jpg",
                "image/png",
                "image/tiff",
                "image/gif",
                "image/raw",
                "image/bmp",
                "image/webp"
        );

    }

    private List<String> getAcceptedDocumentsTypes(){

        return List.of(
                "text/plain",
                "application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                "application/vnd.oasis.opendocument.text",
                "application/pdf"
        );

    }

    private String getImagesDirectoryPath(){
        return "C:/Users/Dariusz/Desktop/gdziejestgrane-react-spring/podium-together-backend/src/main/resources/images";
    }

    private String getDocumentsDirectoryPath(){
        return "C:/Users/Dariusz/Desktop/gdziejestgrane-react-spring/podium-together-backend/src/main/resources/file";
    }


}
