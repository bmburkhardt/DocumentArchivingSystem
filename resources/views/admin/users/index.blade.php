@extends('layouts.admin')




@section('content')

    @if(Session::has('deleted_user'))
        <p class="bg-danger">{{session('deleted_user')}}</p>
    @endif

    <h1>Users</h1>

    <table class="table">
        <thead>
            <tr>
                <th>Id</th>
                <th>Photo</th>
                <th>Name</th>
                <th>Email</th>
                <th>Role</th>
                <th>Status</th>
                <th>Created</th>
                <th>Updated</th>
            </tr>
        </thead>
        <tbody>

        @if($users)
            @foreach($users as $user)
                <tr>
                    <td>{{$user->id}}</td>
                    <td><img><img height="50" src="{{$user->photo ? $user->photo->path : '/images/default.jpg'}}" alt=""></td>
                    <td><a href="{{route('admin.users.edit', $user->id)}}">{{$user->name}}</td>
                    <td>{{$user->email}}</td>
                    <td>{{$user->role ? $user->role->name : 'User has no role'}}</td>
                    <td>{{$user->is_active == 1 ? 'Active' : 'Not Active'}}</td>
                    <td>{{$user->created_at->diffForHumans()}}</td>
                    <td>{{$user->updated_at->diffForHumans()}}</td>
                </tr>
            @endforeach
        @endif

        </tbody>
      </table>



@stop