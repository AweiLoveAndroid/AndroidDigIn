package com.halohoop.androiddigin.frags;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.halohoop.androiddigin.R;
import com.halohoop.androiddigin.categoris.Contents;
import com.halohoop.androiddigin.utils.Utils;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ListDataFragment extends ListFragment {

    // TODO: Customize parameter argument names
    private static final String ITEM_DATAS = "item_datas";
    // TODO: Customize parameters
    private List<Contents.ItemBean> mItemBeens;
    private int mCategory = -1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListDataFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ListDataFragment newInstance(int category) {
        ListDataFragment fragment = new ListDataFragment();
        Bundle args = new Bundle();
        args.putInt(ITEM_DATAS, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mCategory = getArguments().getInt(ITEM_DATAS);
            mItemBeens = Contents.queryItemBeans(mCategory);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        setListAdapter(new MyAdapter());
        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mListener.onListFragmentInteraction(mItemBeens.get(position));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        Utils.log("onDetach");
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Contents.ItemBean itemBean);
    }

    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mItemBeens.size();
        }

        @Override
        public Object getItem(int position) {
            return mItemBeens.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView != null) {
                viewHolder = (ViewHolder) convertView.getTag();
            } else {
                View itemView = View.inflate(getActivity(), R.layout.item, null);
                viewHolder = new ViewHolder();
                viewHolder.tvLeft = (TextView) itemView.findViewById(R.id.item_tv_left);
                viewHolder.tvRight = (TextView) itemView.findViewById(R.id.item_tv_right);
                itemView.setTag(viewHolder);
                convertView = itemView;
            }
            viewHolder.tvLeft.setText(mItemBeens.get(position).category);
            viewHolder.tvRight.setText(mItemBeens.get(position).name);
            return convertView;
        }
    }

    static class ViewHolder {
        TextView tvRight;
        TextView tvLeft;
    }
}