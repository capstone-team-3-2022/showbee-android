package com.team3.showbee.ui.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.team3.showbee.R
import com.team3.showbee.databinding.DialogLogOutBinding
import com.team3.showbee.ui.viewmodel.LogInViewModel

class SetOptionDialog(private val isWriter: Boolean, private val isComment:Boolean): DialogFragment() {

    private var _binding: DialogLogOutBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LogInViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DialogLogOutBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(LogInViewModel::class.java)

        // 레이아웃 배경을 투명하게 해줌, 필수 아님
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnLogoutOk.setOnClickListener {
            buttonClickListener.onLogOutOkClicked()
            dismiss()    // 대화상자를 닫는 함수
        }
        binding.btnLogoutCancel.setOnClickListener {
            buttonClickListener.onLogOutCancelClicked()
            dismiss()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // 인터페이스
    interface OnButtonClickListener {
        fun onLogOutOkClicked()
        fun onLogOutCancelClicked()
    }
    // 클릭 이벤트 설정
    fun setButtonClickListener(buttonClickListener: OnButtonClickListener) {
        this.buttonClickListener = buttonClickListener
    }
    // 클릭 이벤트 실행
    private lateinit var buttonClickListener: OnButtonClickListener
}