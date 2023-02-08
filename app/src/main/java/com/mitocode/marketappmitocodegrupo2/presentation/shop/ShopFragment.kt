package com.mitocode.marketappmitocodegrupo2.presentation.shop

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mitocode.marketappmitocodegrupo2.R
import com.mitocode.marketappmitocodegrupo2.data.model.RegisterCategoryRequest
import com.mitocode.marketappmitocodegrupo2.databinding.FragmentShopBinding
import com.mitocode.marketappmitocodegrupo2.presentation.common.toast
import com.mitocode.marketappmitocodegrupo2.util.Constants
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class ShopFragment : Fragment(R.layout.fragment_shop) {

    private lateinit var binding : FragmentShopBinding

    private val viewModel : ShopViewModel by viewModels()

    private var imageBase64 = ""

    private val loadImage = registerForActivityResult(ActivityResultContracts.GetContent()){ uri ->
        binding.imgCategory.setImageURI(uri)
        val inputStream = uri?.let {
            requireContext().contentResolver.openInputStream(it)
        }
        val imageBitmap = BitmapFactory.decodeStream(inputStream)

        GlobalScope.launch {
            withContext(Dispatchers.Default){
                converterBase64(imageBitmap)
            }
        }

    }

    private val cameraPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted ->
        when{
            isGranted -> startForResult.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
            else -> requireContext().toast("Permiso denegado")
        }
    }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == RESULT_OK){

            val data = result.data
            val imageBitmap = data?.extras?.get("data") as Bitmap
            val uri = getImageUriFromBitmap(requireContext(),imageBitmap)
            cropImage(uri)
        }
    }

    private fun cropImage(uri:Uri?){
        CropImage.activity(uri)
            .setGuidelines(CropImageView.Guidelines.ON)
            .setAspectRatio(16,9)
            .setCropShape(CropImageView.CropShape.RECTANGLE)
            .start(requireContext(),this)
    }

    private fun getImageUriFromBitmap(context: Context, imageBitmap: Bitmap) : Uri {

        val bytes = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver,imageBitmap,"Image",null)
        return Uri.parse(path.toString())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentShopBinding.bind(view)

        events()
        collect()

    }

    private fun collect() {

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect(){ state ->
                    updateUI(state)
                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            val uri = CropImage.getActivityResult(data).uri
            val inputStream = uri?.let {
                requireContext().contentResolver.openInputStream(it)
            }
            val imageBitmap = BitmapFactory.decodeStream(inputStream)
            binding.imgCategory.setImageBitmap(imageBitmap)

            GlobalScope.launch {
                withContext(Dispatchers.Default){
                    converterBase64(imageBitmap)
                }
            }

        }
    }

    private fun updateUI(state: CategoryCreateState) {
        state?.isLoading?.let { condition ->
            if(condition) binding.progress.visibility = View.VISIBLE
            else binding.progress.visibility = View.GONE
        }
        state?.error?.let { error ->
            requireContext().toast(error)
        }
        state?.success?.let { message ->
            requireContext().toast(message)
        }
    }

    private fun events() = with(binding) {

        btnCamera.setOnClickListener {
            cameraPermission.launch(Manifest.permission.CAMERA)
        }

        btnGalery.setOnClickListener {
            loadImage.launch("image/*")
        }

        btnSave.setOnClickListener {

            val name = edtNameCategory.text.toString()
            val imageBase64 = "${Constants.FORMAT_BASE_64}$imageBase64"
            viewModel.saveCategory(RegisterCategoryRequest(name,imageBase64))

        }

    }

    private fun converterBase64(imageBitmap: Bitmap){
        val byteArrayOuputStream = ByteArrayOutputStream()
        imageBitmap?.compress(Bitmap.CompressFormat.JPEG,80,byteArrayOuputStream)
        val bytes =  byteArrayOuputStream.toByteArray()
        imageBase64 = Base64.encodeToString(bytes, Base64.DEFAULT)
    }

}