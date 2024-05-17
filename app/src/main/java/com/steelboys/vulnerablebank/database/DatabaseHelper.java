package com.steelboys.vulnerablebank.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.steelboys.vulnerablebank.domain.User;
import com.steelboys.vulnerablebank.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "vulnerable_bank_cached_users.db";
	private static final int DATABASE_VERSION = 1;

	public DatabaseHelper(
		@Nullable Context context
	) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		/**
		 * USERS(
		 * 	id INTEGER,
		 * 	username TEXT,
		 * 	password TEXT
		 * )
		 */
		db.execSQL("CREATE TABLE cached_users (id INTEGER PRIMARY KEY AUTOINCREMENT"
			+ ", "
			+ "username TEXT"
			+ ", "
			+ "password TEXT"
			+ ")");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE cached_users");
		onCreate(db);
	}

	public void addUser(
		final String username,
		final String password
	) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("username", username);
		contentValues.put("password", password);

		database.insert("cached_users", null, contentValues);

		database.close();
	}

	public User getUserByUsername(String username) {
		if (null == username) {
			return null;
		}
		if (username.isEmpty()) {
			return null;
		}
		SQLiteDatabase database = this.getReadableDatabase();

		String[] projection = {
			"*",
			// Add other columns as needed
		};

		// Define the selection criteria (WHERE clause)
		String selection = "username = ?"; // Example WHERE clause: column_name = ?
		String[] selectionArgs = { username }; // Example value for the WHERE clause

		// Execute the query
		Cursor cursor = database.query(
			"cached_users",  // The table to query
			projection,     // The columns to return
			selection,      // The columns for the WHERE clause
			selectionArgs,  // The values for the WHERE clause
			null,           // Don't group the rows
			null,           // Don't filter by row groups
			null            // The sort order
		);

		List<User> userList = new ArrayList<>();
		// Process the query results
		if (cursor != null) {
			while (cursor.moveToNext()) {
				// Retrieve data from the cursor
				String[] columnNames = cursor.getColumnNames();

				try {
					User user = new User();
					for (String columnName : columnNames) {
						switch (columnName) {
							case "id":
								user.setId(cursor.getInt(cursor.getColumnIndexOrThrow(columnName)));
								break;
							case "username":
								user.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(columnName)));
								break;
							case "password":
								user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(columnName)));
								break;
							default:
								//noop
								break;
						}
					}

					userList.add(user);
				} catch (Exception e) {
					Log.d(Constants.TAG_DATABASE_ERROR, e.getMessage());
				}
				// Process the data as needed
			}
			cursor.close(); // Close the cursor when done
		}

		if (! userList.isEmpty()) {
			return userList.get(0);
		}

		return null;
	}

	public User getUserById(Integer id) {
		if (null == id) {
			return null;
		}
		SQLiteDatabase database = this.getReadableDatabase();

		String[] projection = {
			"*",
			// Add other columns as needed
		};

		// Define the selection criteria (WHERE clause)
		String selection = "id = ?"; // Example WHERE clause: column_name = ?
		String[] selectionArgs = { String.valueOf(id) }; // Example value for the WHERE clause

		// Execute the query
		Cursor cursor = database.query(
			"cached_users",  // The table to query
			projection,     // The columns to return
			selection,      // The columns for the WHERE clause
			selectionArgs,  // The values for the WHERE clause
			null,           // Don't group the rows
			null,           // Don't filter by row groups
			null            // The sort order
		);

		List<User> userList = new ArrayList<>();
		// Process the query results
		if (cursor != null) {
			while (cursor.moveToNext()) {
				// Retrieve data from the cursor
				String[] columnNames = cursor.getColumnNames();

				try {
					User user = new User();
					for (String columnName : columnNames) {
						switch (columnName) {
							case "id":
								user.setId(cursor.getInt(cursor.getColumnIndexOrThrow(columnName)));
								break;
							case "username":
								user.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(columnName)));
								break;
							case "password":
								user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(columnName)));
								break;
							default:
								//noop
								break;
						}
					}

					userList.add(user);
				} catch (Exception e) {
					Log.d(Constants.TAG_DATABASE_ERROR, e.getMessage());
				}
				// Process the data as needed
			}
			cursor.close(); // Close the cursor when done
		}

		if (! userList.isEmpty()) {
			return userList.get(0);
		}

		return null;
	}
}
