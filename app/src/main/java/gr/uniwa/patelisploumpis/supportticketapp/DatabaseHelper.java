package gr.uniwa.patelisploumpis.supportticketapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Cursor cursor;
    private static DatabaseHelper dbInstance;
    private SQLiteDatabase sqLiteDatabaseR = getReadableDatabase();

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

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Singleton Pattern Implementation
    public static synchronized DatabaseHelper getInstance(Context context) {
        if(dbInstance == null){
            dbInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return dbInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
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

        sqLiteDatabase.execSQL(CREATE_TICKETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TICKETS);
            onCreate(sqLiteDatabase);
        }
    }

    // Add new SupportTicket entry to DB
    public void addSupportTicket(SupportTicket ticket) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        sqLiteDatabase.beginTransaction();
        try{
            ContentValues cValues = new ContentValues();
            cValues.put(KEY_TECHNICIAN_NAME, ticket.getTechnicianName());
            cValues.put(KEY_CLIENT_NAME, ticket.getClientName());
            cValues.put(KEY_CLIENT_ADDRESS, ticket.getClientAddress());
            cValues.put(KEY_CLIENT_PHONE, ticket.getClientPhone());
            cValues.put(KEY_CLIENT_EMAIL, ticket.getClientEmail());
            cValues.put(KEY_LABOR_DATE, ticket.getLaborDate());
            cValues.put(KEY_LABOR_TYPE, ticket.getLaborType());
            cValues.put(KEY_LABOR_HOURS, ticket.getLaborHours());
            cValues.put(KEY_LABOR_DESCRIPTION, ticket.getLaborDescription());

            sqLiteDatabase.insertOrThrow(TABLE_TICKETS, null, cValues);
            sqLiteDatabase.setTransactionSuccessful();
        }catch(SQLiteException e){
            e.printStackTrace();
            Log.d("ERROR", "Error while trying to add ticket to database");
        }finally{
            sqLiteDatabase.endTransaction();
        }
    }

    // Retrieve all SupportTickets stored in DB
    @SuppressLint("Range") // Suppress error "value must be >0" for get column index
    public List<SupportTicket> getAllTickets() {
        List<SupportTicket> tickets = new ArrayList<>();
        String TICKETS_SELECT_QUERY = String.format("SELECT * FROM %s", TABLE_TICKETS);
        cursor = sqLiteDatabaseR.rawQuery(TICKETS_SELECT_QUERY, null);

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
        }catch(SQLiteException e){
            e.printStackTrace();
            Log.d("ERROR", "Error while trying to retrieve tickets list from database");
        }finally{
            if(cursor != null && !cursor.isClosed()) { cursor.close(); }
        }

        return tickets;
    }

    // Retrieve SupportTicket by tickedID
    @SuppressLint("Range") // Suppress error "value must be >0" for get column index
    public SupportTicket getTicketByID(int ticketID) {
        SupportTicket ticket = new SupportTicket();
        String TICKET_BY_ID_SELECT_QUERY = String.format("SELECT * FROM %s WHERE %s = %s", TABLE_TICKETS, KEY_TICKET_ID, String.valueOf(ticketID));
        cursor = sqLiteDatabaseR.rawQuery(TICKET_BY_ID_SELECT_QUERY, null);

        try{
            if(cursor.moveToLast()){
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
            }
        }catch(SQLiteException e){
            e.printStackTrace();
            Log.d("ERROR", "Error while trying to retrieve ticket from database");
        }finally{
            if(cursor != null && !cursor.isClosed()) { cursor.close(); }
        }
            return ticket;
    }

    // Retrieve the ticketID of the last SupportTicket stored in DB
    @SuppressLint("Range") // Suppress error "value must be >0" for get column index
    public int getLastTicketID() {
        int lastTicketID = 0;
        String LAST_TICKET_ID_SELECT_QUERY = String.format("SELECT * FROM %s", TABLE_TICKETS);
        cursor = sqLiteDatabaseR.rawQuery(LAST_TICKET_ID_SELECT_QUERY, null);

        try{
            if(cursor.moveToLast()){
                lastTicketID = cursor.getInt(cursor.getColumnIndex(KEY_TICKET_ID));
            }
        }catch(SQLiteException e){
            e.printStackTrace();
            Log.d("ERROR", "Error while trying to retrieve last ticket's id from database");
        }finally{
            if(cursor != null && !cursor.isClosed()) { cursor.close(); }
        }

        return lastTicketID;
    }

}
