package gr.uniwa.patelisploumpis.supportticketapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static DatabaseHandler dbInstance;

    // Database Info
    private static final String DATABASE_NAME = "support_ticket_app";
    private static final int DATABASE_VERSION = 1;

    // Table Name
    private static final String TABLE_TICKETS = "support_tickets";

    // Table Columns
    private static final String KEY_TICKET_ID = "ticketID";
    private static final String KEY_TECHNICIAN_NAME = "technicianName";
    private static final String KEY_CLIENT_NAME = "clientName";
    private static final String KEY_CLIENT_ADDRESS = "clientAddress";
    private static final String KEY_CLIENT_PHONE = "clientPhone";
    private static final String KEY_CLIENT_EMAIL = "clientEmail";
    private static final String KEY_LABOR_DATE = "laborDate";
    private static final String KEY_LABOR_TYPE = "laborType";
    private static final String KEY_LABOR_HOURS = "laborHours";
    private static final String KEY_LABOR_DESCRIPTION = "laborDescription";

    private DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Singleton Pattern Implementation
    public static synchronized DatabaseHandler getInstance(Context context) {
        if(dbInstance == null){
            dbInstance = new DatabaseHandler(context.getApplicationContext());
        }

        return dbInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TICKETS_TABLE = "CREATE TABLE " + TABLE_TICKETS + "(" +
                KEY_TICKET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                KEY_TECHNICIAN_NAME+ " TEXT NOT NULL," +
                KEY_CLIENT_NAME + " TEXT NOT NULL," +
                KEY_CLIENT_ADDRESS + " TEXT NOT NULL," +
                KEY_CLIENT_PHONE + " TEXT NOT NULL," +
                KEY_CLIENT_EMAIL + " TEXT NOT NULL," +
                KEY_LABOR_DATE + " DATE NOT NULL," +
                KEY_LABOR_TYPE + " TEXT NOT NULL," +
                KEY_LABOR_HOURS + " REAL NOT NULL," +
                KEY_LABOR_DESCRIPTION + " TEXT" +
                ")";

        db.execSQL(CREATE_TICKETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TICKETS);
            onCreate(db);
        }
    }

    public void addSupportTicket(SupportTicket ticket) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try{
            ContentValues values = new ContentValues();
            values.put(KEY_TECHNICIAN_NAME, ticket.getTechnicianName());
            values.put(KEY_CLIENT_NAME, ticket.getClientName());
            values.put(KEY_CLIENT_ADDRESS, ticket.getClientAddress());
            values.put(KEY_CLIENT_PHONE, ticket.getClientPhone());
            values.put(KEY_CLIENT_EMAIL, ticket.getClientEmail());
            values.put(KEY_LABOR_DATE, ticket.getLaborDate());
            values.put(KEY_LABOR_TYPE, ticket.getLaborType());
            values.put(KEY_LABOR_HOURS, ticket.getLaborHours());
            values.put(KEY_LABOR_DESCRIPTION, ticket.getLaborDescription());

            db.insertOrThrow(TABLE_TICKETS, null, values);
            db.setTransactionSuccessful();
        }catch(Exception e) {
            Log.d("ERROR", "Error while trying to add ticket to database");
        }finally {
            db.endTransaction();
        }
    }

    @SuppressLint("Range") // Suppress error "value must be >0" for get column index
    public List<SupportTicket> getAllTickets() {
        List<SupportTicket> tickets = new ArrayList<>();
        String TICKETS_SELECT_QUERY = String.format("SELECT * FROM %s", TABLE_TICKETS);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(TICKETS_SELECT_QUERY, null);
        try{
            if(cursor.moveToFirst()){
                do{
                    SupportTicket ticket = new SupportTicket();
                    ticket.setTicketID(cursor.getString(cursor.getColumnIndex(KEY_TICKET_ID)));
                    ticket.setTechnicianName(cursor.getString(cursor.getColumnIndex(KEY_TECHNICIAN_NAME)));
                    ticket.setClientName(cursor.getString(cursor.getColumnIndex(KEY_CLIENT_NAME)));
                    ticket.setClientAddress(cursor.getString(cursor.getColumnIndex(KEY_CLIENT_ADDRESS)));
                    ticket.setClientPhone(cursor.getString(cursor.getColumnIndex(KEY_CLIENT_PHONE)));
                    ticket.setClientEmail(cursor.getString(cursor.getColumnIndex(KEY_CLIENT_EMAIL)));
                    ticket.setLaborDate(cursor.getString(cursor.getColumnIndex(KEY_LABOR_DATE)));
                    ticket.setLaborType(cursor.getString(cursor.getColumnIndex(KEY_LABOR_TYPE)));
                    ticket.setLaborHours(cursor.getInt(cursor.getColumnIndex(KEY_LABOR_HOURS)));
                    ticket.setLaborDescription(cursor.getString(cursor.getColumnIndex(KEY_LABOR_DESCRIPTION)));
                    tickets.add(ticket);
                }while(cursor.moveToNext());
            }
        }catch(Exception e){
            Log.d("ERROR", "Error while trying to get tickets from database");
        }finally{
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }

        return tickets;
    }

    @SuppressLint("Range") // Suppress error "value must be >0" for get column index
    public int getLastTicketID() {
        int lastTicketID = 0;
        String LAST_TICKET_ID_SELECT_QUERY = String.format("SELECT * FROM %s", TABLE_TICKETS);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(LAST_TICKET_ID_SELECT_QUERY, null);
        try{
            if(cursor.moveToLast()){
                lastTicketID = cursor.getInt(cursor.getColumnIndex(KEY_TICKET_ID));
            }
        }catch(Exception e){
            Log.d("ERROR", "Error while trying to get last ticket id from database");
        }finally{
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }

        return lastTicketID;
    }

    public void clearTicketsTable(){
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_TICKETS + "'");
    }
}
