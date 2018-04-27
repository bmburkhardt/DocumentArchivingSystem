@extends('layouts.admin')

@section('content')
    <h1>Documents</h1>

    <table class="table">
        <thead>
            <tr>
                <th>Title</th>
                <th>Owner</th>
                <th>Download</th>
                <th>Summary</th>
                <th>Created</th>
                <th>Updated</th>
            </tr>
        </thead>
        <tbody>

        @if($documents)
            @foreach($documents as $document)
                <tr>
                    <td>{{$document->title}}</td>
                    <td>{{$document->user_id}}</td>
                    <td>Download</td>
                    <td>{{$document->summary}}</td>
                    <td>{{$document->created_at->diffForHumans()}}</td>
                    <td>{{$document->updated_at->diffForHumans()}}</td>
                </tr>
            @endforeach
        @endif
        </tbody>
      </table>
@stop