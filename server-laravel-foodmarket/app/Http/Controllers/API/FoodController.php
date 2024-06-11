<?php

namespace App\Http\Controllers\API;

use App\Helpers\ResponseFormatter;
use App\Http\Controllers\Controller;
use App\Models\Food;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Log;

class FoodController extends Controller
{
    public function all(Request $request)
    {

        $page = $request->query('page', 1);
        $size = $request->query('per_page', 3);
        $types = $request->query('types');


        $id = $request->input('id');
//        $limit = $request->input('limit', 6);
        $name = $request->input('name');
//        $types = $request->input('types');

        $price_from = $request->input('price_from');
        $price_to = $request->input('price_to');

        $rate_from = $request->input('rate_from');
        $rate_to = $request->input('rate_to');

        if ($id) {
            $food = Food::find($id);

            if ($food)
                return ResponseFormatter::success(
                    $food,
                    'Data produk berhasil diambil'
                );
            else
                return ResponseFormatter::error(
                    null,
                    'Data produk tidak ada',
                    404
                );
        }

        $food = Food::query();

        if ($name)
            $food->where('name', 'like', '%' . $name . '%');

        if ($types)
            $food->where('types', 'like', '%' . $types . '%');

        if ($price_from)
            $food->where('price', '>=', $price_from);

        if ($price_to)
            $food->where('price', '<=', $price_to);

        if ($rate_from)
            $food->where('rate', '>=', $rate_from);

        if ($rate_to)
            $food->where('rate', '<=', $rate_to);

        return ResponseFormatter::success(
            $food->paginate(perPage: $size, page: $page),
            'Data list produk berhasil diambil'
        );
    }

    public function types(Request $request)
    {
        $types = $request->query('types');


        $food = Food::query();

        $food->where('types', 'like', '%' . $types . '%');



        return ResponseFormatter::success(
            $food->paginate(perPage: $food->count()),
            'Data list produk berhasil diambil'
        );
    }
}
