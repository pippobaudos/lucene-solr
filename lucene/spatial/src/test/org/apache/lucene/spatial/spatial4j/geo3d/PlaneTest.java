package org.apache.lucene.spatial.spatial4j.geo3d;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test basic plane functionality.
 */
public class PlaneTest {


  @Test
  public void testIdenticalPlanes() {
    final GeoPoint p = new GeoPoint(0.123, -0.456);
    final Plane plane1 = new Plane(p, 0.0);
    final Plane plane2 = new Plane(p, 0.0);
    assertTrue(plane1.isNumericallyIdentical(plane2));
    final Plane plane3 = new Plane(p, 0.1);
    assertFalse(plane1.isNumericallyIdentical(plane3));
    final Vector v1 = new Vector(0.1, -0.732, 0.9);
    final double constant = 0.432;
    final Vector v2 = new Vector(v1.x * constant, v1.y * constant, v1.z * constant);
    final Plane p1 = new Plane(v1, 0.2);
    final Plane p2 = new Plane(v2, 0.2 * constant);
    assertTrue(p1.isNumericallyIdentical(p2));
  }

  @Test
  public void testInterpolation() {
    // [X=0.35168818443386646, Y=-0.19637966197066342, Z=0.9152870857244183],
    // [X=0.5003343189532654, Y=0.522128543226148, Z=0.6906861469771293],

    final GeoPoint start = new GeoPoint(0.35168818443386646, -0.19637966197066342, 0.9152870857244183);
    final GeoPoint end = new GeoPoint(0.5003343189532654, 0.522128543226148, 0.6906861469771293);

    // [A=-0.6135342247741855, B=0.21504338363863665, C=0.28188192383666794, D=0.0, side=-1.0] internal? false;
    final Plane p = new Plane(-0.6135342247741855, 0.21504338363863665, 0.28188192383666794, 0.0);

    final GeoPoint[] points = p.interpolate(start, end, new double[]{0.25, 0.50, 0.75});

    for (GeoPoint point : points) {
      assertTrue(p.evaluateIsZero(point));
    }
  }
}

