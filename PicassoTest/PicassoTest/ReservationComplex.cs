﻿using System;
using System.Collections.Generic;
using System.Text;

namespace PicassoTest
{
    public class ReservationComplex
    {
        public User MadeBy { get; set; }
        public float Price { get; set; }
        public bool CanBeCancelledBy(User user)
        {
            if(user.IsAdmin)
            {
                return true;
            }
            if(user == MadeBy)
            {
                return true;
            }

            return false;
        }

        public float PayReservation(User user)
        {
            throw new NotImplementedException();
        }

        /// <summary>
        /// Method that checks whether the reservation can be paid or not and if it is possible it proceeds to pay the reservatio.
        /// </summary>
        /// <param name="user">User to pay the reservation</param>
        /// <returns>0 if the reservation is paid and the price of the reservation is substracted from the money of the user,</returns>
        public float PayReservation(UserComplex user)
        {
            if (user==null) throw new ArgumentNullException();

            if (user.Money>= this.Price)
            {
                return 0;
            }
            else
            {
                throw new ArgumentOutOfRangeException();
            }
        }
    }

    public class UserComplex
    {
        public float Money { get; set; }
        public bool IsAdmin { get; set; }
    }
}
