package net.thumbtack.school.file;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.util.Random;

import net.thumbtack.school.colors.v3.Color;
import net.thumbtack.school.colors.v3.ColorException;
import net.thumbtack.school.figures.v3.ColoredRectangle;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import net.thumbtack.school.figures.v3.Rectangle;
import net.thumbtack.school.ttschool.Trainee;
import net.thumbtack.school.ttschool.TrainingException;

public class TestFileService {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();


    @Test
    public void testFileReadWriteByteArray1() {
        byte[] arrayToWrite = {0, 1, 5, -34, 67, -123};
        try {
            File file = folder.newFile("test.dat");
            FileService.writeByteArrayToBinaryFile(file, arrayToWrite);
            assertTrue(file.exists());
            assertEquals(arrayToWrite.length, file.length());
            byte[] arrayRead = FileService.readByteArrayFromBinaryFile(file);
            assertArrayEquals(arrayToWrite, arrayRead);
        } catch (IOException ex) {
            fail();
        }
    }

    @Test
    public void testFileReadWriteByteArray2() {
        byte[] arrayToWrite = {0, 1, 5, -34, 67, -123};
        try {
            String fileName = folder.newFile("test.dat").getPath();
            FileService.writeByteArrayToBinaryFile(fileName, arrayToWrite);
            File file = new File(fileName);
            assertTrue(file.exists());
            assertEquals(arrayToWrite.length, file.length());
            byte[] arrayRead = FileService.readByteArrayFromBinaryFile(fileName);
            assertArrayEquals(arrayToWrite, arrayRead);
        } catch (IOException ex) {
            fail();
        }
    }

    @Test
    public void testByteStreamReadWriteByteArray() {
        byte[] arrayToWrite = {0, 1, 5, -34, 67, -123};
        try {
            byte[] result = FileService.writeAndReadByteArrayUsingByteStream(arrayToWrite);
            assertArrayEquals(new byte[]{0, 5, 67}, result);
        } catch (IOException ex) {
            fail();
        }
    }

    @Test
    public void testFileReadWriteByteArray1Buffered() {
        byte[] arrayToWrite = {0, 1, 5, -34, 67, -123};
        try {
            File file = folder.newFile("test.dat");
            FileService.writeByteArrayToBinaryFileBuffered(file, arrayToWrite);
            assertTrue(file.exists());
            assertEquals(arrayToWrite.length, file.length());
            byte[] arrayRead = FileService.readByteArrayFromBinaryFileBuffered(file);
            assertArrayEquals(arrayToWrite, arrayRead);
        } catch (IOException ex) {
            fail();
        }
    }

    @Test
    public void testFileReadWriteByteArray2Buffered() {
        byte[] arrayToWrite = {0, 1, 5, -34, 67, -123};
        try {
            String fileName = folder.newFile("test.dat").getPath();
            FileService.writeByteArrayToBinaryFileBuffered(fileName, arrayToWrite);
            File file = new File(fileName);
            assertTrue(file.exists());
            assertEquals(arrayToWrite.length, file.length());
            byte[] arrayRead = FileService.readByteArrayFromBinaryFileBuffered(fileName);
            assertArrayEquals(arrayToWrite, arrayRead);
        } catch (IOException ex) {
            fail();
        }
    }

    @Test
    public void testFileReadWriteRectangleBinary() {
        Rectangle rectToWrite = new Rectangle(10000, 10000, 20000, 20000);
        try {
            File file = folder.newFile("test.dat");
            FileService.writeRectangleToBinaryFile(file, rectToWrite);
            assertTrue(file.exists());
            assertEquals(16, file.length());
            Rectangle rectRead = FileService.readRectangleFromBinaryFile(file);
            assertEquals(rectToWrite, rectRead);
        } catch (IOException ex) {
            fail();
        }
    }

    @Test
    public void testFileReadWriteColoredRectangleBinary() throws ColorException {
        ColoredRectangle rectToWrite = new ColoredRectangle(10000, 10000, 20000, 20000, Color.RED);
        try {
            File file = folder.newFile("test.dat");
            FileService.writeColoredRectangleToBinaryFile(file, rectToWrite);
            assertTrue(file.exists());
            assertEquals(21, file.length());
            ColoredRectangle rectRead = FileService.readColoredRectangleFromBinaryFile(file);
            assertEquals(rectToWrite, rectRead);
        } catch (IOException ex) {
            fail();
        }
    }

    @Test
    public void testFileReadRectangleArrayBinary() {
        int count = 5;
        Rectangle[] rectsToWrite = new Rectangle[count];
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            rectsToWrite[i] = new Rectangle(random.nextInt(), random.nextInt(), random.nextInt(), random.nextInt());
        }
        try {
            File file = folder.newFile("test.dat");
            FileService.writeRectangleArrayToBinaryFile(file, rectsToWrite);
            assertTrue(file.exists());
            assertEquals(count * 16, file.length());
            Rectangle[] rectsRead = FileService.readRectangleArrayFromBinaryFileReverse(file);
            for (int i = 0; i < rectsRead.length / 2; i++) {
                Rectangle temp = rectsRead[i];
                rectsRead[i] = rectsRead[rectsRead.length - i - 1];
                rectsRead[rectsRead.length - i - 1] = temp;
            }
            assertArrayEquals(rectsToWrite, rectsRead);
        } catch (IOException ex) {
            fail();
        }
    }

    @Test
    public void testFileReadWriteRectangleTextOneLine() {
        Rectangle rectToWrite = new Rectangle(10000, 10000, 20000, 20000);
        try {
            File file = folder.newFile("test.txt");
            FileService.writeRectangleToTextFileOneLine(file, rectToWrite);
            assertTrue(file.exists());
            assertEquals(1, Files.readAllLines(file.toPath()).size());
            Rectangle rectRead = FileService.readRectangleFromTextFileOneLine(file);
            assertEquals(rectToWrite, rectRead);
        } catch (IOException ex) {
            fail();
        }
    }

    @Test
    public void testFileReadWriteRectangleTextFourLines() {
        Rectangle rectToWrite = new Rectangle(10000, 10000, 20000, 20000);
        try {
            File file = folder.newFile("test.txt");
            FileService.writeRectangleToTextFileFourLines(file, rectToWrite);
            assertTrue(file.exists());
            assertEquals(4, Files.readAllLines(file.toPath()).size());
            Rectangle rectRead = FileService.readRectangleFromTextFileFourLines(file);
            assertEquals(rectToWrite, rectRead);
        } catch (IOException ex) {
            fail();
        }
    }

    @Test
    public void testFileReadWriteTraineeTextOneLine() throws NumberFormatException, TrainingException {
        Trainee traineeToWrite = new Trainee("Иван", "Иванов", 2);
        try {
            File file = folder.newFile("test.txt");
            FileService.writeTraineeToTextFileOneLine(file, traineeToWrite);
            assertTrue(file.exists());
            assertEquals(1, Files.readAllLines(file.toPath()).size());
            Trainee traineeRead = FileService.readTraineeFromTextFileOneLine(file);
            assertEquals(traineeToWrite, traineeRead);
        } catch (IOException ex) {
            fail();
        }
    }

    @Test
    public void testFileReadWriteTraineeTextThreeLines() throws NumberFormatException, TrainingException {
        Trainee traineeToWrite = new Trainee("Иван", "Иванов", 2);
        try {
            File file = folder.newFile("test.txt");
            FileService.writeTraineeToTextFileThreeLines(file, traineeToWrite);
            assertTrue(file.exists());
            assertEquals(3, Files.readAllLines(file.toPath()).size());
            Trainee traineeRead = FileService.readTraineeFromTextFileThreeLines(file);
            assertEquals(traineeToWrite, traineeRead);
        } catch (IOException ex) {
            fail();
        }
    }

    @Test
    public void testFileSerializeDeserializeTraineeBinary() throws TrainingException, ClassNotFoundException {
        Trainee traineeToWrite = new Trainee("Иван", "Иванов", 2);
        try {
            File file = folder.newFile("test.txt");
            FileService.serializeTraineeToBinaryFile(file, traineeToWrite);
            assertTrue(file.exists());
            Trainee traineeRead = FileService.deserializeTraineeFromBinaryFile(file);
            assertEquals(traineeToWrite, traineeRead);
        } catch (IOException ex) {
            fail();
        }
    }

    @Test
    public void testStringSerializeDeserializeTraineeJson() throws TrainingException {
        Trainee traineeToWrite = new Trainee("Иван", "Иванов", 2);
        String json = FileService.serializeTraineeToJsonString(traineeToWrite);
        Trainee traineeRead = FileService.deserializeTraineeFromJsonString(json);
        assertEquals(traineeToWrite, traineeRead);
    }

    @Test
    public void testFileSerializeDeserializeTraineeJson() throws TrainingException {
        Trainee traineeToWrite = new Trainee("Иван", "Иванов", 2);
        try {
            File file = folder.newFile("test.txt");
            FileService.serializeTraineeToJsonFile(file, traineeToWrite);
            assertTrue(file.exists());
            Trainee traineeRead = FileService.deserializeTraineeFromJsonFile(file);
            assertEquals(traineeToWrite, traineeRead);
        } catch (IOException ex) {
            fail();
        }
    }
    @Test
    public void testThrowsIOException() {
        Method[] declaredMethods = FileService.class.getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (method.getName().equals("serializeTraineeToJsonString") || method.getName().equals("deserializeTraineeFromJsonString")) {
                continue;
            }
            if (!Modifier.isPublic(method.getModifiers())) {
                continue;
            }
            Class<?>[] exceptionTypes = method.getExceptionTypes();
            boolean throwIOException = false;
            for (Class<?> exception : exceptionTypes) {
                if (exception == IOException.class) {
                    throwIOException = true;
                    break;
                }
            }
            if (!throwIOException) {
                fail("Every public method must throw IOException");
            }
        }
    }

}
