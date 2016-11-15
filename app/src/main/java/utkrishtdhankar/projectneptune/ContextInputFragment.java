package utkrishtdhankar.projectneptune;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by Shreyak Kumar on 12-11-2016.
 */
public class ContextInputFragment extends DialogFragment implements View.OnClickListener {
    // A reference to the database. Used when adding/editing contexts
    DatabaseHelper databaseHelper;

    // The place where the user types in their context name
    private EditText contextEditText;
    Spinner colorDropDown;

    // The button that is pressed when the user has added the context
    private Button contextAddButton;

    // Variables for updating contexts
    int openedForEdit = 0;
    TaskContext taskContext = new TaskContext();

    /**
     * Default constructor
     */
    public ContextInputFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    /**
     * Creates this input fragment
     * @param title The title of this fragment
     * @param context The context this is called in
     * @return The new input fragment that we created
     */
    public static ContextInputFragment newInstance(String title, Context context) {
        ContextInputFragment frag = new ContextInputFragment();
        // Set the arguments for the fragment
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);

        return frag;
    }

    /**
     *
     * @param title The title of the fragment
     * @param contextText The content of the context text
     * @param contextColor The color of the context
     * @return The new fragment that we return
     */
    public static ContextInputFragment newInstance(String title,String contextText,int contextColor) {
        ContextInputFragment frag = new ContextInputFragment();

        // Set the arguments for the fragment
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("contextText", contextText);
        args.putInt("contextColor", contextColor);
        frag.setArguments(args);

        return frag;
    }

    /**
     * Inflates this fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        if(bundle.get("title") == "edit") {
            openedForEdit = 1;
        }else{
            openedForEdit = 0;
        }

        return inflater.inflate(R.layout.context_input_fragment, container);

    }

    /**
     * Sets up all of the things (the edit texts, etc.) with references from the code
     * And also does some other housekeeping like opening up the soft keyboard
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        colorDropDown = (Spinner) view.findViewById(R.id.color_spinner);

        // Create an ArrayAdapter using the string array and a default colorDropDown layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.color_titles, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the colorDropDown list
        colorDropDown.setAdapter(adapter);

        // Get fields from view
        contextEditText = (EditText) view.findViewById(R.id.addContextInput);
        contextAddButton = (Button) view.findViewById(R.id.addTaskbutton) ;
        contextAddButton.setOnClickListener(this);

        databaseHelper = new DatabaseHelper(getActivity());

        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);

        if(openedForEdit == 1){
            taskContext.setName(getArguments().getString("contextText"));
            taskContext.setColor(getArguments().getInt("contextColor"));
            contextEditText.setText(taskContext.getName());
            for(int i = 0; i < adapter.getCount(); i++){
                if(Color.parseColor(adapter.getItem(i).toString()) == taskContext.getColor()){
                    colorDropDown.setSelection(i);
                }
            }

        }


        // Show soft keyboard automatically and request focus to field
        contextEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    /**
     * Called when the user clicks the add button
     * @param view The view that this was a part of
     */
    @Override
    public void onClick(View view) {
        // Fill out the new context
        String newContextName = contextEditText.getText().toString();
        int newContextColor = Color.parseColor(colorDropDown.getSelectedItem().toString());
        TaskContext newContext = new TaskContext(newContextName,newContextColor);

        if(openedForEdit == 1){
            //call the editing function
        }else{
            // Add said context to the database
            databaseHelper.addContext(newContext);
        }


        //Reloading the fragment so that values from tables are updated
        Fragment fragment = new ContextsFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,fragment).addToBackStack(null).commit();

        //Closes the pop-up
        dismiss();
    }
}
