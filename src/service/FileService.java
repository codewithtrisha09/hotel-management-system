package service;
import model.Room;
import model.Booking;
import java.io.*;
import java.util.ArrayList;
public class FileService {
    private static final String ROOM_FILE = "rooms.txt";
    private static final String BOOKING_FILE = "bookings.txt";
    public static void saveRooms(ArrayList<Room> rooms) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ROOM_FILE))) {
            for (Room r : rooms) {
                pw.println(r.getRoomNo() + "," + r.getType() + "," + r.getPrice());}} 
        catch (Exception e) { e.printStackTrace(); }}
    public static ArrayList<Room> loadRooms() {
        ArrayList<Room> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ROOM_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                list.add(new Room(
                        Integer.parseInt(data[0]),
                        data[1],
                        Double.parseDouble(data[2]) ));}
        } catch
        (Exception e) { }
        return list; }
    public static void saveBookings(ArrayList<Booking> bookings) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(BOOKING_FILE))) {
            for (Booking b : bookings) {
                pw.println(b.getName() + "," + b.getRoomNo() + "," + b.getBill());}
        } catch (Exception e) { e.printStackTrace(); }}
    public static ArrayList<Booking> loadBookings() {
        ArrayList<Booking> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(BOOKING_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                list.add(new Booking(
                        data[0],
                        Integer.parseInt(data[1]),
                        false,
                        1,
                        false,
                        Double.parseDouble(data[2]),
                        1,
                        0,
                        false)); }} catch (Exception e) { } return list; }}
