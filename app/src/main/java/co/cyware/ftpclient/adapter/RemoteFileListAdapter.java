package co.cyware.ftpclient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPFile;

import co.cyware.ftpclient.R;
import co.cyware.ftpclient.presenter.RemoteFileListPresenter;

/**
 * Created by Anoop S S on 27/4/16.
 */
public class RemoteFileListAdapter extends RecyclerView.Adapter<RemoteFileListAdapter.RemoteFileListItemHolder> implements View.OnClickListener {

    private final RemoteFileListPresenter.OnRemoteFileSelectCallback mRemoteFileSelectCallback;
    private FTPFile[] mFileList;

    public RemoteFileListAdapter(RemoteFileListPresenter.OnRemoteFileSelectCallback remoteFileSelectCallback) {
        this.mRemoteFileSelectCallback = remoteFileSelectCallback;
    }

    @Override
    public RemoteFileListItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.remote_file_list_item_layout, null);
        return new RemoteFileListItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RemoteFileListItemHolder holder, int position) {
        holder.mRemoteFileName.setText(mFileList[position].getName());
        holder.mRemoteFileSize.setText(FileUtils.byteCountToDisplaySize(mFileList[position].getSize()));
        holder.mRemoteFileName.setTag(position);
        holder.mRemoteFileName.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return null == mFileList ? 0 : mFileList.length;
    }

    public FTPFile getItemAtPos(int position) {
        return mFileList[position];
    }

    public void setFileList(FTPFile[] fileList) {
        this.mFileList = fileList;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if(null != mRemoteFileSelectCallback){
            mRemoteFileSelectCallback.onSelectFileAtPosition((Integer) view.getTag());
        }
    }

    public static class RemoteFileListItemHolder extends RecyclerView.ViewHolder {

        protected TextView mRemoteFileName;
        protected TextView mRemoteFileSize;

        public RemoteFileListItemHolder(View itemView) {
            super(itemView);

            mRemoteFileName = (TextView) itemView.findViewById(R.id.remote_file_name);
            mRemoteFileSize = (TextView) itemView.findViewById(R.id.remote_file_size);
        }
    }
}
